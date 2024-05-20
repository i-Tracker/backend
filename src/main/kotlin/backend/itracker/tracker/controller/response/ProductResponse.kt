package backend.itracker.tracker.controller.response

import java.math.BigDecimal

data class ProductResponse(
    val id: Long,
    val title: String,
    val category: String,
    val inch: Int,
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val label: String,
    val imageUrl: String,
    val isOutOfStock: Boolean
)
