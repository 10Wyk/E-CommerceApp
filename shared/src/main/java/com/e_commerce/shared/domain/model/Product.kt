package com.e_commerce.shared.domain.model

import androidx.compose.ui.graphics.Color
import com.e_commerce.shared.presentation.CategoryBlue
import com.e_commerce.shared.presentation.CategoryGreen
import com.e_commerce.shared.presentation.CategoryPurple
import com.e_commerce.shared.presentation.CategoryRed
import com.e_commerce.shared.presentation.CategoryYellow
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val thumbnail: String,
    val category: String,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: Double,
    val isPopular: Boolean = false,
    val isDiscounted: Boolean = false,
    val isNew: Boolean = false
)

enum class ProductCategory(
    val title: String,
    val color: Color
) {
    Protein(
        title = "Protein",
        color = CategoryYellow
    ),
    Creatine(
        title = "Creatine",
        color = CategoryBlue
    ),
    PreWorkout(
        title = "Pre-Workout",
        color = CategoryPurple
    ),
    Gainers(
        title = "Gainers",
        color = CategoryRed
    ),
    Accessories(
        title = "Accessories",
        color = CategoryGreen
    );

    companion object {
        fun getByTitle(title: String): ProductCategory =
            entries.find { it.title == title } ?: Protein
    }
}