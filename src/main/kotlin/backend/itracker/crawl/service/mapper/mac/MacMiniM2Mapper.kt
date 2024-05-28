package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.MacCategory
import backend.itracker.crawl.service.response.MacCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_MINI_M2 = "2023 Mac mini M2"

@Component
class MacMiniM2Mapper : MacMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return MAC_MINI_M2 == subCategory
    }

    override fun toDomain(product: DefaultProduct): Mac {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1]
        val category = MacCategory.MAC_MINI
        val size = ""
        val color = ""

        val chipAndCpu = names[1].split(" ")
        var chip = ""
        var cpu = ""
        if (chipAndCpu.size == 3) {
            chip = "${chipAndCpu[0]} ${chipAndCpu[1]}"
            cpu = chipAndCpu.last().replace("코어", "")
        } else {
            chip = chipAndCpu.first()
            cpu = chipAndCpu.last().replace("코어", "")
        }

        val gpu = names[2].replace("코어", "")
        val storage = names[3]
        val memory = names[4]

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
