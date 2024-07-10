package backend.itracker.schedule.service.notification.dto

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.absoluteValue

private val PARTNERS_PROTOCOL = "https://"

data class PriceChangeNotificationInfo(
    val name: String,
    val discountRate: Int,
    val productDetail: String,
    val priceDrop: BigDecimal,
    val rateDrop: Int,
    val currentPrice: BigDecimal,
    val category: String,
    val productId: Long,
    val productPartnersLink: String,
) {

    fun mutableMap(): MutableMap<String, String> {
        return mutableMapOf(
            "#{name}" to name,
            "#{discount_rate}" to discountRate.absoluteValue.toString(),
            "#{product_detail}" to productDetail,
            "#{price_drop}" to toKrw(priceDrop),
            "#{rate_drop}" to rateDrop.absoluteValue.toString(),
            "#{current_price}" to toKrw(currentPrice),
            "#{category}" to category.lowercase(),
            "#{product_id}" to productId.toString(),
            "#{product_partners_link}" to productPartnersLink.replace(PARTNERS_PROTOCOL, "")
        )
    }

    private fun toKrw(price: BigDecimal): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(price.setScale(0, RoundingMode.HALF_UP))
    }
}
