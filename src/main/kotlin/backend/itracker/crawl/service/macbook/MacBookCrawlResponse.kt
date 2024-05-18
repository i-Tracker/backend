package backend.itracker.crawl.service.macbook

import backend.itracker.crawl.domain.MacBook
import backend.itracker.crawl.domain.MacBookPrice
import backend.itracker.crawl.response.DefaultProduct
import java.math.BigDecimal

data class MacBookCrawlResponse(
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
        fun of(product: DefaultProduct): MacBookCrawlResponse {
            return MacBookDesirializer.deserialize(product)
        }
    }

    fun toDomain(): MacBook {
        return MacBook(
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
            price = MacBookPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                discountPrice = discountPrice
            ),
            productLink = productLink,
            thumbnail = thumbnail
        )
    }
}
