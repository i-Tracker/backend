package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.MacCategory
import backend.itracker.crawl.service.response.MacCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_MINI = "Mac mini (intel)"

@Component
class MacMiniIntelMapper : MacMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return MAC_MINI == subCategory
    }

    override fun toDomain(product: DefaultProduct): Mac {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = 2020
        val category = MacCategory.MAC_MINI
        val size = ""
        val color = ""

        val cpu = names[1].replace("코어", "")
        val gpu = ""

        val chip = "intel"
        val memory = names[2]
        val storage = names[3].split(" ").last()

        return MacCrawlResponse(
            coupangId = product.productId,
            company = company,
            name = product.name,
            category = category,
            chip = chip,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            color = color,
            size = size,
            releaseYear = releaseYear,
            discountPercentage = product.price.discountPercentage,
            basePrice = product.price.basePrice,
            discountPrice = product.price.discountPrice,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
            isOutOfStock = product.price.isOutOfStock
        ).toDomain()
    }
}
