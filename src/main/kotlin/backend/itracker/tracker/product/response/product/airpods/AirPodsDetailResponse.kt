package backend.itracker.tracker.product.response.product.airpods

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.AirPodsCategory
import backend.itracker.tracker.product.response.product.CommonPriceInfo
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import java.math.BigDecimal

class AirPodsDetailResponse(
    val id: Long,
    val title: String,
    val generation: Int,
    val canWirelessCharging: Boolean,
    val chargingType: String,
    val category: String,
    val color: String,
    val label: Boolean,
    val imageUrl: String,
    val coupangUrl: String,
    val isOutOfStock: Boolean,

    isFavorite: Boolean,
    discountPercentage: Int,
    currentPrice: BigDecimal,
    allTimeHighPrice: BigDecimal,
    allTimeLowPrice: BigDecimal,
    averagePrice: BigDecimal,
    priceInfos: List<CommonPriceInfo>,
) : CommonProductDetailModel(
    isFavorite,
    discountPercentage,
    currentPrice,
    allTimeHighPrice,
    allTimeLowPrice,
    averagePrice,
    priceInfos
) {
    companion object {
        fun of(
            airPods: AirPods,
            isFavorite: Boolean = false
        ): CommonProductDetailModel {
            val name = when (airPods.category) {
                AirPodsCategory.AIRPODS -> "에어팟"
                AirPodsCategory.AIRPODS_PRO -> "에어팟 프로"
                AirPodsCategory.AIRPODS_MAX -> "에어팟 맥스"
            }
            return AirPodsDetailResponse(
                id = airPods.id,
                title = "${airPods.company} ${airPods.releaseYear} $name ${airPods.generation}세대",
                generation = airPods.generation,
                canWirelessCharging = airPods.canWirelessCharging,
                chargingType = airPods.chargingType,
                category = airPods.category.name.lowercase(),
                color = airPods.color,
                label = airPods.isAllTimeLowPrice(),
                imageUrl = airPods.thumbnail,
                coupangUrl = airPods.partnersLink.ifBlank { airPods.productLink },
                isFavorite = isFavorite,
                discountPercentage = airPods.findDiscountPercentage(),
                currentPrice = airPods.findCurrentPrice(),
                isOutOfStock = airPods.isOutOfStock(),
                allTimeHighPrice = airPods.findAllTimeHighPrice(),
                allTimeLowPrice = airPods.findAllTimeLowPrice(),
                averagePrice = airPods.findAveragePrice(),
                priceInfos = airPods.getRecentPricesByPeriod(SIX_MONTH).airPodsPrices
                    .map { CommonPriceInfo.of(it.createdAt, it.currentPrice) }
            )
        }
    }
}
