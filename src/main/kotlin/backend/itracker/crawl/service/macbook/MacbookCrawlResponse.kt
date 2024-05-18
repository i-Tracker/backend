package backend.itracker.crawl.service.macbook

import backend.itracker.crawl.domain.Macbook
import backend.itracker.crawl.domain.MacbookPrice
import backend.itracker.crawl.response.DefaultProduct
import java.math.BigDecimal

data class MacbookCrawlResponse(
    val company: String,
    val name: String,
    val type: String,
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
    val thumbnail: String
) {
    companion object {
        fun of(product: DefaultProduct): MacbookCrawlResponse {
            return MacbookDesirializer.deserialize(product)
        }
    }

    fun toDomain(): Macbook {
        return Macbook(
            company = company,
            name = name,
            type = type,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            language = language,
            color = color,
            size = size,
            releaseYear = releaseYear,
            price = mutableListOf(
                MacbookPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                discountPrice = discountPrice
                )
            ),
            productLink = productLink,
            thumbnail = thumbnail
        )
    }
}
