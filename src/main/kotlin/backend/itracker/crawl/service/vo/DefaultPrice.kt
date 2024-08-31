package backend.itracker.crawl.service.vo

import java.math.BigDecimal

private val none = DefaultPrice(
            discountPercentage = 0,
            basePrice = BigDecimal.ZERO,
            discountPrice = BigDecimal.ZERO,
            isOutOfStock = "품절"
        )

data class DefaultPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
    val isOutOfStock: Boolean
) {
    companion object {
        fun none() = none
    }

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


