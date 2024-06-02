package backend.itracker.tracker.service.response.product

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.Period

interface CommonProductModel


abstract class CommonProductDetailModel(
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val allTimeHighPrice: BigDecimal,
    val allTimeLowPrice: BigDecimal,
    val averagePrice: BigDecimal,
    val commonPriceInfos: List<CommonPriceInfo>
) {

    companion object {
        @JvmStatic
        protected val SIX_MONTH = Period.of(0, 6, 0)!!
    }
}

data class CommonPriceInfo(
    val date: LocalDateTime,
    val currentPrice: BigDecimal
)
