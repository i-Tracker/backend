package backend.itracker.tracker.product.response.product

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

interface CommonProductModel {

    fun discountPercentage(): Int
}


abstract class CommonProductDetailModel(
    val isFavorite: Boolean,
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val allTimeHighPrice: BigDecimal,
    val allTimeLowPrice: BigDecimal,
    val averagePrice: BigDecimal,
    val notificationCount: Long,
    val priceInfos: List<CommonPriceInfo>
) {

    companion object {
        @JvmStatic
        protected val SIX_MONTH = Period.of(0, 6, 0)!!
    }
}

data class CommonPriceInfo(
    val date: String,
    val currentPrice: BigDecimal
){
    companion object {
        fun of(date: LocalDateTime, currentPrice: BigDecimal): CommonPriceInfo {
            val changedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            return CommonPriceInfo(changedDate, currentPrice)
        }
    }
}

