package com.e_commerce.shared.presentation.component.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (String) -> Unit
) {
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
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
            Text(
                text = product.title,
                fontSize = FontSize.MEDIUM,
                color = Resources.appColors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.description,
                fontSize = FontSize.REGULAR,
                color = Resources.appColors.textPrimary.copy(alpha = 0.5f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.category != ProductCategory.Accessories.title)
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.Weight),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${product.weight}g",
                            color = Resources.appColors.textPrimary,
                            fontSize = FontSize.EXTRA_SMALL,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.offset(y = 1.dp)
                        )
                    }

                Text(
                    modifier = Modifier.weight(1f),
                    color = Resources.appColors.textSecondary,
                    text = "$ ${product.price}",
                    fontSize = FontSize.EXTRA_REGULAR,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProductCardPrev() {
    PreviewTheme {
        ProductCard(
            product = Product(
                id = "12321",
                title = "NUTREND 100% WHEY PROTEIN",
                description = LoremIpsum(50).values.iterator().next(),
                thumbnail = "https://lh3.googleusercontent.com/a/ACg8ocLXYNBviRwAueE54FKIN79lD2WFld2zPaUZ75Q3gPy5-g9R2R8=s96-c",
                category = ProductCategory.Creatine.title,
                weight = 2020,
                price = 5000.0
            ),
            onClick = {}
        )
    }
}