package backend.itracker.crawl.response

import backend.itracker.crawl.domain.MacBook
import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigDecimal

val logger = KotlinLogging.logger {}

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
            if (product.category == "MacBook Air M3 13 기본형"
                || product.category == "MacBook Air M3 15 기본형"
                || product.category == "MacBook Air 13 M3 CTO"
                || product.category == "MacBook Air 15 M3 CTO"
                || product.category == "MacBook Air 13 M2 기본형"
                || product.category == "MacBook Air 15 M2 기본형"
                || product.category == "MacBook Air 13 M2 CTO"
                || product.category == "MacBook Air 15 M2 CTO"
            ) {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = "${title[2]} ${title[3]}"
                val size = title[4].toInt()
                val color = names[1]
                val cpu = names[2]
                val gpu = names[3]
                val storage = names[4]
                val memory = names[5]
                val language = names[7]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = gpu,
                    storage = storage,
                    memory = memory,
                    language = language,
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }

            if (product.category == "MacBook Pro 14 M3 기본형") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = "${title[2]} ${title[3]}"
                val size = title[4].toInt()
                val color = names[1]
                val cpu = names[2]
                val gpu = names[3]
                val storage = names[4]
                val memory = names[5]
                val language = names[6]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = gpu,
                    storage = storage,
                    memory = memory,
                    language = language,
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }
            if (product.category == "MacBook Pro 16 M3 기본형") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = "${title[2]} ${title[3]}"
                val size = title[4].toInt()
                val color = names[1]
                val cpu = names[2]
                val gpu = names[3]
                val storage = names[4]
                val memory = names[5]
                val language = names[6]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = gpu,
                    storage = storage,
                    memory = memory,
                    language = language,
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }
            if (product.category == "MacBook Pro 14 M3 CTO") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = "${title[2]} ${title[3]}"
                val size = title[4].toInt()
                val color = names[1]
                val cpu = names[2]
                val gpu = names[3]
                val storage = names[4]
                val memory = names[5]
                val language = names[6]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = gpu,
                    storage = storage,
                    memory = memory,
                    language = language,
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }
            if (product.category == "MacBook Pro 16 M3 CTO") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val ctoSize = names[0].split(" ").size
                if (ctoSize > 6) {
                    val title = names[0].split(" ")
                    val company = title[0]
                    val releaseYear = title[1].toInt()
                    val type = "${title[2]} ${title[3]}"
                    val size = title[4].toInt()
                    val cpu = "${title[2]} ${title[3]} ${title[4]} ${title[5]}"
                    val gpu = "${title[6]} ${title[7]}"

                    val color = names[1]
                    val storage = names[2]
                    val memory = names[3]
                    val language = names[4]

                    return MacBookCrawlResponse(
                        company = company,
                        name = product.name,
                        type = type,
                        cpu = cpu,
                        gpu = gpu,
                        storage = storage,
                        memory = memory,
                        language = language,
                        color = color,
                        size = size,
                        releaseYear = releaseYear,
                        discountPercentage = product.price.discountPercentage,
                        basePrice = product.price.basePrice,
                        discountPrice = product.price.discountPrice,
                        productLink = product.productLink,
                        thumbnail = product.thumbnailLink
                    )
                }
            }

            if (product.category == "MacBook Pro 16 M1") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = title[2]
                val size = title[3].toInt()
                val color = names[1]
                val cpu = names[2]
                val gpu = names[3]
                val storage = names[4]
                val memory = names[5]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = gpu,
                    storage = storage,
                    memory = memory,
                    language = "",
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }
            if (product.category == "MacBook Air 13 M1") {
                val names = product.name.split(",")
                    .map { it.trim() }
                    .toList()
                val title = names[0].split(" ")
                val company = title[0]
                val releaseYear = title[1].toInt()
                val type = "${title[2]} ${title[3]}"
                val size = title[4].toInt()
                val color = names[1]
                val cpu = names[2]
                val storage = names[3]
                val memory = names[4]
                return MacBookCrawlResponse(
                    company = company,
                    name = product.name,
                    type = type,
                    cpu = cpu,
                    gpu = "",
                    storage = storage,
                    memory = memory,
                    language = "",
                    color = color,
                    size = size,
                    releaseYear = releaseYear,
                    discountPercentage = product.price.discountPercentage,
                    basePrice = product.price.basePrice,
                    discountPrice = product.price.discountPrice,
                    productLink = product.productLink,
                    thumbnail = product.thumbnailLink
                )
            }
            throw IllegalStateException("Not supported category: ${product.category}")
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
            discountPercentage = discountPercentage,
            discountPrice = discountPrice,
            basePrice = basePrice,
            productLink = productLink,
            thumbnail = thumbnail
        )
    }
}
