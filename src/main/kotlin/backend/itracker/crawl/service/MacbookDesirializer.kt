package backend.itracker.crawl.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type1
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type2
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type4
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type5
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type6
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type7
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type8
import backend.itracker.crawl.service.MacbookDesirializer.Parser.type9
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct

enum class MacbookDesirializer(
    private val subCategory: String,
    private val function: (DefaultProduct) -> MacbookCrawlResponse
) {

    MAC_BOOK_AIR_M3_13("MacBook Air M3 13 기본형", ::type1),
    MAC_BOOK_AIR_M3_13_CTO("MacBook Air 13 M3 CTO", ::type1),
    MAC_BOOK_AIR_M3_15("MacBook Air M3 15 기본형", ::type1),
    MAC_BOOK_AIR_M3_15_CTO("MacBook Air 15 M3 CTO", ::type1),

    MAC_BOOK_PRO_M3_14("MacBook Pro 14 M3 기본형", ::type9),
    MAC_BOOK_PRO_M3_14_CTO("MacBook Pro 14 M3 CTO", ::type9),
    MAC_BOOK_PRO_M3_16("MacBook Pro 16 M3 기본형", ::type6),
    MAC_BOOK_PRO_M3_16_CTO("MacBook Pro 16 M3 CTO", ::type8),

    MAC_BOOK_AIR_M2_13("MacBook Air 13 M2 기본형", ::type7),
    MAC_BOOK_AIR_M2_15("MacBook Air 15 M2 기본형", ::type1),
    MAC_BOOK_AIR_M2_13_CTO("MacBook Air 13 M2 CTO", ::type2),
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
            val size = title[4].toInt()
            val color = names[1]
            val processor = names[2].split(" ")
            val chip = processor.first()
            val cpu = processor.last()
            val gpu = names[3].split(" ").first()
            val storage = names[4]
            val memory = names[5]
            val language = names[7]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_AIR,
                chip = chip,
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
            val size = title[4].toInt()
            val color = names[1]
            val processor = names[2].split(" ")
            val chip = processor.first()
            val cpu = processor.last()
            val gpuProcessor =  names[3].split(" ")
            val gpu = if (gpuProcessor.first() == "GPU") {
                gpuProcessor[1]
            } else {
                gpuProcessor.first()
            }
            val storage = names[4]
            val memory = names[5]
            val language = names[7]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_AIR,
                chip = chip,
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
            val size = title[3].toInt()
            val color = names[1]
            val processor = names[2].split(" ")
            val chip = "${processor[0]} ${processor[1]}"
            val cpu = processor[2]
            val gpu = names[3].split(" ").last()
            val storage = names[4]
            val memory = names[5]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_PRO,
                chip = chip,
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
            val size = title[4].toInt()
            val color = names[1]
            val chip = names[2]
            val storage = names[3]
            val memory = names[4]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_AIR,
                chip = chip,
                cpu = "",
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

        @JvmStatic
        fun type6(product: DefaultProduct): MacbookCrawlResponse {
            val names = product.name.split(",")
                .map { it.trim() }
                .toList()
            val title = names[0].split(" ")
            val company = title[0]
            val releaseYear = title[1].toInt()
            val size = title[4].toInt()
            val color = names[1]
            val processor = names[2].split(" ")
            val chip = "${processor[0]} ${processor[1]}"
            val cpu = processor[2]
            val gpu = names[3].split(" ").first()
            val storage = names[4]
            val memory = names[5]
            val language = names[6]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_PRO,
                chip = chip,
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
        fun type7(product: DefaultProduct): MacbookCrawlResponse {
            val names = product.name.split(",")
                .map { it.trim() }
                .toList()
            val title = names[0].split(" ")
            val company = title[0]
            val releaseYear = title[1].toInt()
            val size = title[4].toInt()

            val color = names[1]
            val processor = names[2].split(" ")
            val chip = processor.first()
            val cpu = processor.last()
            val gpu = names[3].split(" ").last()
            val storage = names[4]
            val memory = names[5]
            val language = names[7]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_PRO,
                chip = chip,
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
        fun type8(product: DefaultProduct): MacbookCrawlResponse {
            val names = product.name.split(",")
                .map { it.trim() }
                .toList()
            val title = names[0].split(" ")
            val company = title[0]
            val releaseYear = title[1].toInt()
            val size = title[4].toInt()
            val chip = "${title[5]} ${title[6]}"
            val cpu = title[7]
            val gpu = title[9]
            val color = names[1]
            val storage = names[2]
            val memory = names[3]
            val language = names[4]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_PRO,
                chip = chip,
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
        fun type9(product: DefaultProduct): MacbookCrawlResponse {
            val names = product.name.split(",")
                .map { it.trim() }
                .toList()
            val title = names[0].split(" ")
            val company = title[0]
            val releaseYear = title[1].toInt()
            val size = title[4].toInt()
            val color = names[1]
            val processor = names[2].split(" ")

            var chip: String
            var cpu: String
            if (processor.size == 2) {
                chip = processor.first()
                cpu = processor[1]
            } else {
                chip = "${processor[0]} ${processor[1]}"
                cpu = processor[2]
            }
            val gpu = names[3].split(" ").first()
            val storage = names[4]
            val memory = names[5]
            val language = names[6]

            return MacbookCrawlResponse(
                coupangId = product.productId,
                company = company,
                name = product.name,
                category = ProductCategory.MACBOOK_PRO,
                chip = chip,
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
    }

    companion object {
        fun deserialize(product: DefaultProduct): MacbookCrawlResponse {
            val category = entries.firstOrNull { it.subCategory == product.category }
                ?: throw IllegalStateException("지원하지 않는 맥북 카테고리 입니다. category : ${product.category}")

            return category.function(product)
        }
    }
}
