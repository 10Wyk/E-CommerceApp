package com.e_commerce.shared.presentation.component.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.presentation.Resources

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (String) -> Unit
) {
    val painter = rememberAsyncImagePainter(model = product.thumbnail)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(size = 12.dp))
            .border(
                width = 1.dp,
                color = Resources.appColors.borderIdle,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(Resources.appColors.surfaceLighter)
            .clickable { onClick(product.id) }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = Resources.appColors.borderIdle,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(all = 12.dp)
        ) {

        }
    }
}