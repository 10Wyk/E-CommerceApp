package com.wyk.productsoverview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wyk.productsoverview.component.MainProductCard

@Composable
fun ProductsOverview() {
    val viewModel = viewModel<ProductsOverviewViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val listState = rememberLazyListState()
    val centeredIndex: Int? by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2
            layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                kotlin.math.abs(itemCenter - viewportCenter)
            }?.index
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(
                    items = state.newProducts
                        .filter { it.isNew }
                        .sortedBy { it.createdAt }
                        .take(6),
                    key = { index, item -> item.id }
                ) { index, product ->
                    val isLarge = index == centeredIndex
                    val animatedScale by animateFloatAsState(
                        targetValue = if (isLarge) 1f else 0.8f,
                        animationSpec = tween(300)
                    )

                    MainProductCard(
                        modifier = Modifier
                            .scale(animatedScale)
                            .height(300.dp)
                            .fillParentMaxWidth(0.6f),
                        product = product,
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}