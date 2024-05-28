package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.MacCategory
import backend.itracker.crawl.service.response.MacCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_STUDIO_M2 = "2023 Mac Studio M2"

@Component
class MacStudioM2Mapper : MacMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return MAC_STUDIO_M2 == subCategory
    }

    override fun toDomain(product: DefaultProduct): Mac {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1].replace("년", "")
        val category = MacCategory.MAC_STUDIO
        val size = ""
        val color = ""

        val chipAndCpu = names[1].split(" ")
        val chip = "${chipAndCpu[0]} ${chipAndCpu[1]}"
        val cpu = chipAndCpu.last().replace("코어", "")

        val gpu = names[2].split(" ").last().replace("코어", "")

        val memory = names[3]

        val storage = names[4]

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
