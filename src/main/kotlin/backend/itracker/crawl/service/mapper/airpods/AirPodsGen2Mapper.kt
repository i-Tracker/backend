package backend.itracker.crawl.service.mapper.airpods

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.AirPodsCategory
import backend.itracker.crawl.service.response.AirPodsCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val AIR_PODS_GEN_2 = "AirPods 2세대"

@Component
class AirPodsGen2Mapper : AirPodsMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return AIR_PODS_GEN_2 == subCategory
    }

    override fun toDomain(product: DefaultProduct): AirPods {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = "2019"
        val category = AirPodsCategory.AIRPODS
        val generation = title[2].replace("세대", "")
        val canWirelessCharging = title[3] == "유선"
        val chargingType = "Lighting 8-pin"

        return AirPodsCrawlResponse(
            coupangId = product.productId,
            name = product.name,
            company = company,
            releaseYear = releaseYear,
            category = category,
            generation = generation,
            canWirelessCharging = canWirelessCharging,
            chargingType = chargingType,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
            basePrice = product.price.basePrice,
            discountPercentage = product.price.discountPercentage,
            currentPrice = product.price.discountPrice,
            isOutOfStock = product.price.isOutOfStock
        ).toDomain()
    }
}
