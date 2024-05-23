package backend.itracker.crawl.macbook.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

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

    private fun findAveragePrice(): BigDecimal {
        return macbookPrices.sumOf { it.currentPrice }
            .divide(BigDecimal.valueOf(macbookPrices.size.toLong()), 2, HALF_UP)
    }
}
