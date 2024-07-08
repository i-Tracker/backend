package backend.itracker.crawl.airpods.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.Period

private val PERCENT_BASE = BigDecimal.valueOf(100)

@Embeddable
class AirPodsPrices(
    @OneToMany(mappedBy = "airPods", cascade = [CascadeType.PERSIST])
    val airPodsPrices: MutableList<AirPodsPrice> = mutableListOf()
) {

    fun add(targetAirPodsPrice: AirPodsPrice) {
        airPodsPrices.add(targetAirPodsPrice)
    }

    fun findTodayDiscountPercentage(): Int {
        val currentPrice = findCurrentPrice()
        val averagePrice = findAveragePrice()

        val priceDiff = (currentPrice - averagePrice) / averagePrice
        return priceDiff.multiply(PERCENT_BASE).toInt()
    }

    fun findCurrentPrice(): BigDecimal {
        return airPodsPrices.maxBy { it.createdAt }.currentPrice
    }

    fun findAveragePrice(): BigDecimal {
        return airPodsPrices.sumOf { it.currentPrice }
            .divide(BigDecimal.valueOf(airPodsPrices.size.toLong()), 2, RoundingMode.HALF_UP)
    }

    fun isOutOfStock(): Boolean {
        return airPodsPrices.maxBy { it.createdAt }.isOutOfStock
    }

    fun findAllTimeHighPrice(): BigDecimal {
        return airPodsPrices.maxOf { it.currentPrice }
    }

    fun findAllTimeLowPrice(): BigDecimal {
        return airPodsPrices.minOf { it.currentPrice }
    }

    fun isAllTimeLowPrice(): Boolean {
        val todayPrice = airPodsPrices.maxBy { it.createdAt }.currentPrice

        return todayPrice <= findAllTimeLowPrice() && findTodayDiscountPercentage() < 0
    }

    fun isDecreasingPrice(): Boolean {
        return todayPrice().isSmallerThan(yesterdayPrice())
    }

    fun findPriceDiffFromYesterday(): BigDecimal {
        return yesterdayPrice().currentPrice.minus(todayPrice().currentPrice)
    }

    fun findDiscountRateDiffFromYesterday(): Int {
        val todayPrice = todayPrice().currentPrice
        val yesterdayPrice = yesterdayPrice().currentPrice

        val priceDiff = todayPrice.minus(yesterdayPrice) / yesterdayPrice
        return priceDiff.multiply(PERCENT_BASE).toInt()
    }

    private fun todayPrice(): AirPodsPrice {
        return airPodsPrices.maxBy { it.createdAt }
    }

    private fun yesterdayPrice(): AirPodsPrice {
        val sortedPrices = airPodsPrices.sortedBy { it.createdAt }
        return sortedPrices[sortedPrices.size - 2]
    }

    fun getRecentPricesByPeriod(period: Period): AirPodsPrices {
        val startDate = LocalDateTime.now().minus(period)
        val prices = airPodsPrices.filter { it.createdAt.isAfter(startDate) }
            .sortedBy { it.createdAt }

        return AirPodsPrices(prices.toMutableList())
    }
}
