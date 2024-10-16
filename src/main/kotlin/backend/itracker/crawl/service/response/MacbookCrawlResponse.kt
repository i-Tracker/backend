package backend.itracker.crawl.service.response

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.domain.MacbookPrice
import java.math.BigDecimal

data class MacbookCrawlResponse(
    val coupangId: Long,
    val company: String,
    val name: String,
    val category: MacbookCategory,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val language: String,
    val color: String,
    val size: Int,
    val releaseYear: Int,
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
    val productLink: String,
    val thumbnail: String,
    val isOutOfStock: Boolean
) {

    fun toDomain(): Macbook {
        val macbook = Macbook(
            coupangId = coupangId,
            company = company,
            name = name,
            category = category,
            chip = chip,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            language = language,
            color = color,
            size = size,
            releaseYear = releaseYear,
            productLink = productLink,
            thumbnail = thumbnail
        )

        macbook.addPrice(
            MacbookPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                currentPrice = discountPrice,
                isOutOfStock = isOutOfStock
            )
        )
        return macbook
    }
}
