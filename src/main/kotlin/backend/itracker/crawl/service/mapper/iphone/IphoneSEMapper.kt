package backend.itracker.crawl.service.mapper.iphone

import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.iphone.domain.IphoneCategory
import backend.itracker.crawl.service.response.IphoneCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class IphoneSEMapper : IphoneMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return "iPhone SE" == subCategory
    }

    override fun toDomain(product: DefaultProduct): Iphone {
        val coupangId = product.productId

        val names = product.name.split(", ")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val generation = title[4].toCharArray().first().toString()

        val category = IphoneCategory.IPHONE_SE

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
