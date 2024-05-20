package backend.itracker.crawl.service

import backend.itracker.crawl.service.MacbookDesirializer.Parser.type1
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type2
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type3
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type4
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type5
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct

enum class MacbookDesirializer(
    private val category: String,
    private val function: (DefaultProduct) -> MacbookCrawlResponse
) {

    MAC_BOOK_AIR_M3_13("MacBook Air M3 13 기본형", ::type1),
    MAC_BOOK_AIR_M3_13_CTO("MacBook Air 13 M3 CTO", ::type1),
    MAC_BOOK_AIR_M3_15("MacBook Air M3 15 기본형", ::type1),
    MAC_BOOK_AIR_M3_15_CTO("MacBook Air 15 M3 CTO", ::type1),

    MAC_BOOK_PRO_M3_14("MacBook Pro 14 M3 기본형", ::type2),
    MAC_BOOK_PRO_M3_14_CTO("MacBook Pro 14 M3 CTO", ::type2),
    MAC_BOOK_PRO_M3_16("MacBook Pro 16 M3 기본형", ::type2),
    MAC_BOOK_PRO_M3_16_CTO("MacBook Pro 16 M3 CTO", ::type3),

    MAC_BOOK_AIR_M2_13("MacBook Air 13 M2 기본형", ::type1),
    MAC_BOOK_AIR_M2_15("MacBook Air 15 M2 기본형", ::type1),
    MAC_BOOK_AIR_M2_13_CTO("MacBook Air 13 M2 CTO", ::type1),
    MAC_BOOK_AIR_M2_15_CTO("MacBook Air 15 M2 CTO", ::type1),

    MAC_BOOK_AIR_M1_13("MacBook Air 13 M1", ::type5),
    MAC_BOOK_AIR_M1_16("MacBook Pro 16 M1", ::type4);

    object Parser {
        @JvmStatic
        fun type1(product: DefaultProduct): MacbookCrawlResponse {
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

            return MacbookCrawlResponse(
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
                thumbnail = product.thumbnailLink,
                isOutOfStock = product.isOutOfStock
            )
        }

        @JvmStatic
        fun type2(product: DefaultProduct): MacbookCrawlResponse {
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

            return MacbookCrawlResponse(
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
                thumbnail = product.thumbnailLink,
                isOutOfStock = product.isOutOfStock
            )
        }

        @JvmStatic
        fun type3(product: DefaultProduct): MacbookCrawlResponse {
            val names = product.name.split(",")
                .map { it.trim() }
                .toList()
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

            return MacbookCrawlResponse(
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
                thumbnail = product.thumbnailLink,
                isOutOfStock = product.isOutOfStock
            )
        }

        @JvmStatic
        fun type4(product: DefaultProduct): MacbookCrawlResponse {
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

            return MacbookCrawlResponse(
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
                thumbnail = product.thumbnailLink,
                isOutOfStock = product.isOutOfStock
            )
        }

        @JvmStatic
        fun type5(product: DefaultProduct): MacbookCrawlResponse {
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

            return MacbookCrawlResponse(
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
                thumbnail = product.thumbnailLink,
                isOutOfStock = product.isOutOfStock
            )
        }
    }

    companion object {
        fun deserialize(product: DefaultProduct): MacbookCrawlResponse {
            val category = entries.firstOrNull { it.category == product.category }
                ?: throw IllegalStateException("지원하지 않는 맥북 카테고리 입니다. category : ${product.category}")

            return category.function(product)
        }
    }
}
