package com.e_commerce.shared.presentation.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources

@Composable
fun CategoryPickerDialog(
    selectedCategory: ProductCategory,
    onConfirmClick: (ProductCategory) -> Unit,
    onDismiss: () -> Unit
) {
    val categories = remember {
        ProductCategory.entries.toList()
    }

    var pickedCategory by remember(selectedCategory) {
        mutableStateOf(selectedCategory)
    }

    AlertDialog(
        containerColor = Resources.appColors.surface,
        title = {
            Text(
                text = "Select a Category",
                fontSize = FontSize.EXTRA_MEDIUM,
                color = Resources.appColors.textPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = categories,
                        key = { it.ordinal }
                    ) { product ->
                        val selected = product == pickedCategory
                        val backgroundColor by animateColorAsState(
                            targetValue = if (selected) product.color.copy(alpha = 0.2f) else Resources.appColors.surface
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(6.dp))
                                .background(backgroundColor)
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        pickedCategory = product
                                    },
                                    interactionSource = null,
                                    indication = ripple(bounded = true)
                                )
                                .padding(vertical = 16.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.title,
                                color = Resources.appColors.textPrimary,
                                fontSize = FontSize.REGULAR,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            AnimatedVisibility(
                                visible = selected
                            ) {
                                Icon(
                                    painter = painterResource(Resources.Icon.Checkmark),
                                    contentDescription = null,
                                    tint = Resources.appColors.iconPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick(pickedCategory)
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Resources.appColors.textSecondary
                )
            ) {
                Text(
                    text = "Confirm",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Resources.appColors.textPrimary.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}