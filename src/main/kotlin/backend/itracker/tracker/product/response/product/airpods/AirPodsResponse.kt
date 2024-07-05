package backend.itracker.tracker.product.response.product.airpods

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.AirPodsCategory
import backend.itracker.tracker.product.response.product.CommonProductModel
import java.math.BigDecimal

data class AirPodsResponse(
    val id: Long,
    val title: String,
    val generation: Int,
    val canWirelessCharging: Boolean,
    val chargingType: String,
    val category: String,
    val color: String,
    val label: Boolean,
    val imageUrl: String,
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    val notificationCount: Long,
) : CommonProductModel {

    companion object {
        fun of(airPods: AirPods, notificationCount: Long): AirPodsResponse {
            val name = when (airPods.category) {
                AirPodsCategory.AIRPODS -> "에어팟"
                AirPodsCategory.AIRPODS_PRO -> "에어팟 프로"
                AirPodsCategory.AIRPODS_MAX -> "에어팟 맥스"
            }
            return AirPodsResponse(
                id = airPods.id,
                title = "${airPods.company} ${airPods.releaseYear} $name ${airPods.generation}세대",
                generation = airPods.generation,
                canWirelessCharging = airPods.canWirelessCharging,
                chargingType = airPods.chargingType,
                category = airPods.category.name.lowercase(),
                color = airPods.color,
                label = airPods.isAllTimeLowPrice(),
                imageUrl = airPods.thumbnail,
                discountPercentage = airPods.findDiscountPercentage(),
                currentPrice = airPods.findCurrentPrice(),
                isOutOfStock = airPods.isOutOfStock(),
                notificationCount = notificationCount,
            )
        }
    }

    override fun discountPercentage(): Int {
        return discountPercentage
    }
}
