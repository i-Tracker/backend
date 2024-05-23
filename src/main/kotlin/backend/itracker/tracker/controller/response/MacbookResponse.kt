package backend.itracker.tracker.controller.response

import backend.itracker.tracker.service.response.CommonProductModel
import java.math.BigDecimal

data class MacbookResponse(
    val id: Long,
    val title: String,
    val category: String,
    val size: Int,
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val color: String,
    val imageUrl: String,
    val isOutOfStock: Boolean
) : CommonProductModel

