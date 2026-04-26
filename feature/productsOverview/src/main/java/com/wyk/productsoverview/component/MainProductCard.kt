package com.wyk.productsoverview.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.RobotoCondensedMediumFont

@Composable
fun MainProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(size = 12.dp))
            .clickable { onClick(product.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(product.thumbnail)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Product thumbnail",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Black.copy(alpha = 0f)
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0.0f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = product.title,
                fontSize = FontSize.EXTRA_MEDIUM,
                fontWeight = FontWeight.Medium,
                color = Resources.appColors.textWhite,
                fontFamily = RobotoCondensedMediumFont(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description,
                fontSize = FontSize.REGULAR,
                lineHeight = FontSize.REGULAR * 1.3f,
                color = Resources.appColors.textWhite.copy(alpha = 0.5f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedContent(
                    targetState = product.category
                ) { category ->
                    if (ProductCategory.valueOf(category) == ProductCategory.Accessories) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(Resources.Icon.Weight),
                                contentDescription = "Weight icon",
                                tint = Resources.appColors.iconWhite
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${product.weight}g",
                                fontSize = FontSize.EXTRA_SMALL,
                                color = Resources.appColors.textWhite
                            )
                        }
                    }
                }
                Text(
                    text = "$${product.price}",
                    fontSize = FontSize.EXTRA_REGULAR,
                    color = Resources.appColors.textBrand,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}