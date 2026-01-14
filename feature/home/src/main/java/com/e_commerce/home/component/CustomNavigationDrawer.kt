package com.e_commerce.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.e_commerce.home.model.DrawerItem
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.RobotoCondensedMediumFont

@Composable
fun CustomNavigationDrawer(
    onProfileClick: () -> Unit,
    onContactClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .padding(
                horizontal = 12.dp
            )
            .verticalScroll(state = rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            letterSpacing = 0.sp,
            text = "nutrisport".uppercase(),
            fontSize = FontSize.EXTRA_LARGE,
            fontFamily = BebasNeueRegularFont(),
            textAlign = TextAlign.Center,
            color = Resources.appColors.textSecondary
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Healthy Lifestyle",
            letterSpacing = 0.sp,
            fontSize = FontSize.REGULAR,
            fontFamily = RobotoCondensedMediumFont(),
            textAlign = TextAlign.Center,
            color = Resources.appColors.textPrimary
        )

        Spacer(modifier = Modifier.height(50.dp))

        DrawerItem.entries.take(5).forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(
                        onClick = {
                            when (item) {
                                DrawerItem.Profile -> onProfileClick.invoke()
                                DrawerItem.Contact -> onContactClick.invoke()
                                DrawerItem.SingOut -> onSignOutClick.invoke()
                                else -> {}
                            }
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true)
                    )
                    .padding(all = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp),
                    tint = Resources.appColors.iconPrimary
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = item.title,
                    fontSize = FontSize.EXTRA_REGULAR,
                    color = Resources.appColors.textPrimary
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable(
                    onClick = onAdminClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true)
                )
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(DrawerItem.Admin.icon),
                contentDescription = DrawerItem.Admin.title,
                modifier = Modifier.size(24.dp),
                tint = Resources.appColors.iconPrimary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = DrawerItem.Admin.title,
                fontSize = FontSize.EXTRA_REGULAR,
                color = Resources.appColors.textPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
@Preview
private fun CustomNavigationDrawerPrev() {
    PreviewTheme {
        CustomNavigationDrawer(
            {},
            {},
            {},
            {}
        )
    }
}