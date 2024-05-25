package backend.itracker.crawl.service.vo

import java.math.BigDecimal

data class DefaultPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
    val isOutOfStock: Boolean
) {
    constructor(
        discountPercentage: Int,
        basePrice: BigDecimal,
        discountPrice: BigDecimal,
        isOutOfStock: String
    ) : this(
        discountPercentage = discountPercentage,
        basePrice = basePrice,
        discountPrice = discountPrice,
        isOutOfStock = isOutOfStock == "일시품절"
    )
}


