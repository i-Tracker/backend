package backend.itracker.crawl.service.mapper.iphone

import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.iphone.domain.IphoneCategory
import backend.itracker.crawl.service.response.IphoneCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val IPHONE_15 = "iPhone 15"
private const val IPHONE_14 = "iPhone 14"

@Component
class IphoneMapper : IphoneMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return subCategory.contains(IPHONE_15) ||
                subCategory.contains(IPHONE_14)
    }

    override fun toDomain(product: DefaultProduct): Iphone {
        val coupangId = product.productId

        val names = product.name.split(", ")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val generation = title[3]

        val category = when (title.size) {
            5 -> {
                IphoneCategory.IPHONE_NORMAL
            }

            6 -> {
                if (title[4] == "Plus") {
                    IphoneCategory.IPHONE_PLUS
                }
                IphoneCategory.IPHONE_PRO
            }

            7 -> {
                IphoneCategory.IPHONE_PRO_MAX
            }

            else -> {
                throw IllegalStateException("Iphone 카테고리를 설정할 수 없습니다. name: ${product.name}")
            }
        }

        val color = names[1]

        val storage = names[2]

        return IphoneCrawlResponse(
            coupangId = coupangId,
            company = company,
            name = product.name,
            category = category,
            color = color,
            storage = storage,
            generation = generation,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
            price = product.price
        ).toDomain()
    }
}
