package backend.itracker.crawl.macbook.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.time.LocalDateTime
import java.time.Period

private val PERCENT_BASE = BigDecimal.valueOf(100)

@Embeddable
class MacbookPrices(
    @OneToMany(mappedBy = "macbook", cascade = [CascadeType.PERSIST])
    val macbookPrices: MutableList<MacbookPrice> = mutableListOf()
) {

    fun add(targetPrice: MacbookPrice) {
        macbookPrices.add(targetPrice)
    }

    fun findTodayDiscountPercentage(): Int {
        val currentPrice = findCurrentPrice()
        val averagePrice = findAveragePrice()

        val priceDiff = (currentPrice - averagePrice) / averagePrice
        return priceDiff.multiply(PERCENT_BASE).toInt()
    }

    fun findCurrentPrice(): BigDecimal {
        return macbookPrices.maxBy { it.createdAt }.currentPrice
    }

    fun findAveragePrice(): BigDecimal {
        return macbookPrices.sumOf { it.currentPrice }
            .divide(BigDecimal.valueOf(macbookPrices.size.toLong()), 2, HALF_UP)
    }

    fun findAllTimeHighPrice(): BigDecimal {
        return macbookPrices.maxOf { it.currentPrice }
    }

    fun findAllTimeLowPrice(): BigDecimal {
        return macbookPrices.minOf { it.currentPrice }
    }

    fun isOutOfStock(): Boolean {
        return macbookPrices.maxBy { it.createdAt }.isOutOfStock
    }

    fun isAllTimeLowPrice(): Boolean {
        val todayPrice = macbookPrices.maxBy { it.createdAt } .currentPrice

        return todayPrice <= findAllTimeLowPrice() && findTodayDiscountPercentage() < 0
    }

    fun getRecentPricesByPeriod(period: Period): MacbookPrices {
        val startDate = LocalDateTime.now().minus(period)
        val prices = macbookPrices.filter { it.createdAt.isAfter(startDate) }
            .sortedBy { it.createdAt }

        return MacbookPrices(prices.toMutableList())
    }
}
