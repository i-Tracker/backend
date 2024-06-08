package backend.itracker.crawl.service.response

import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.iphone.domain.IphoneCategory
import backend.itracker.crawl.iphone.domain.IphonePrice
import backend.itracker.crawl.service.vo.DefaultPrice
import java.math.BigDecimal

data class IphoneCrawlResponse(
    val coupangId: Long,
    val company: String,
    val name: String,
    val category: IphoneCategory,
    val color: String,
    val storage: String,
    val generation: Int,
    val productLink: String,
    val thumbnail: String,
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean
) {
    constructor(
        coupangId: Long,
        company: String,
        name: String,
        category: IphoneCategory,
        color: String,
        storage: String,
        generation: String,
        productLink: String,
        thumbnail: String,
        price: DefaultPrice,
    ) : this(
        coupangId,
        company,
        name,
        category,
        color,
        storage,
        generation.toInt(),
        productLink,
        thumbnail,
        discountPercentage = price.discountPercentage,
        basePrice = price.basePrice,
        currentPrice = price.discountPrice,
        isOutOfStock = price.isOutOfStock
    )

    fun toDomain(): Iphone {
        return Iphone(
            coupangId = coupangId,
            company = company,
            name = name,
            category = category,
            color = color,
            storage = storage,
            generation = generation,
            productLink = productLink,
            thumbnail = thumbnail,
        ).apply {
            addPrice(
                IphonePrice(
                    discountPercentage = discountPercentage,
                    basePrice = basePrice,
                    currentPrice = currentPrice,
                    isOutOfStock = isOutOfStock
                )
            )
        }
    }
}
