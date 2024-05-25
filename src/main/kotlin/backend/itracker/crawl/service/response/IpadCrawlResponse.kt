package backend.itracker.crawl.service.response

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.ipad.domain.IpadCategory
import backend.itracker.crawl.ipad.domain.IpadPrice
import java.math.BigDecimal

data class IpadCrawlResponse(
    val coupangId: Long,
    val company: String,
    val name: String,
    val category: IpadCategory,
    val chip: String,
    val storage: String,
    val color: String,
    val size: String,
    val releaseYear: Int,
    val isCellular: Boolean,
    val generation: String,
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
        category: IpadCategory,
        chip: String,
        storage: String,
        color: String,
        size: String,
        releaseYear: String,
        isCellular: Boolean,
        generation: String,
        productLink: String,
        thumbnail: String,
        discountPercentage: String,
        basePrice: BigDecimal,
        currentPrice: BigDecimal,
        isOutOfStock: Boolean
    ) : this(
        coupangId = coupangId,
        company = company,
        name = name,
        category = category,
        chip = chip,
        storage = storage,
        color = color,
        size = size,
        releaseYear = releaseYear.toInt(),
        isCellular = isCellular,
        generation = generation,
        productLink = productLink,
        thumbnail = thumbnail,
        discountPercentage = discountPercentage.toInt(),
        basePrice = basePrice,
        currentPrice = currentPrice,
        isOutOfStock = isOutOfStock
    )

    fun toDomain() : Ipad {
        val ipad = Ipad(
            coupangId = coupangId,
            company = company,
            name = name,
            category = category,
            chip = chip,
            storage = storage,
            color = color,
            size = size,
            releaseYear = releaseYear,
            isCellular = isCellular,
            generation = generation,
            productLink = productLink,
            thumbnail = thumbnail
        )

        ipad.addPrice(
            IpadPrice(
                basePrice = basePrice,
                discountPercentage = discountPercentage,
                currentPrice = currentPrice,
                isOutOfStock = isOutOfStock
            )
        )

        return ipad
    }
}
