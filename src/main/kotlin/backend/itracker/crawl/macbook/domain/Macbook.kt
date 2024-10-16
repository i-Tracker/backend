package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Period

@Entity
@Table(name = "macbook")
class Macbook(
    val coupangId: Long,
    val company: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val language: String,
    val color: String,
    val size: Int,
    val releaseYear: Int,

    @Enumerated(EnumType.STRING)
    val category: MacbookCategory,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    var partnersLink: String = "",

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: MacbookPrices = MacbookPrices(),

    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(targetPrices: MacbookPrices) {
        targetPrices.macbookPrices.forEach(this::addPrice)
    }

    fun addPrice(targetPrice: MacbookPrice) {
        prices.add(targetPrice)
        targetPrice.changeMacbook(this)
    }

    fun findCurrentPrice(): BigDecimal {
        return prices.findCurrentPrice()
    }

    fun findAveragePrice(): BigDecimal {
        return prices.findAveragePrice()
    }

    fun findAllTimeHighPrice(): BigDecimal {
        return prices.findAllTimeHighPrice()
    }

    fun findAllTimeLowPrice(): BigDecimal {
        return prices.findAllTimeLowPrice()
    }

    fun findDiscountPercentage(): Int {
        return prices.findTodayDiscountPercentage()
    }

    fun isOutOfStock(): Boolean {
        return prices.isOutOfStock()
    }

    fun isAllTimeLowPrice(): Boolean {
        return prices.isAllTimeLowPrice()
    }

    fun isDecreasingPrice(): Boolean {
        return prices.isDecreasingPrice()
    }

    fun findPriceDiffFromYesterday(): BigDecimal {
        return prices.findPriceDiffFromYesterday()
    }

    fun findDiscountRateDiffFromYesterday(): Int {
        return prices.findDiscountRateDiffFromYesterday()
    }

    fun changePartnersLink(partnersLink: String) {
        this.partnersLink = partnersLink
    }

    fun getRecentPricesByPeriod(period: Period): MacbookPrices {
        return prices.getRecentPricesByPeriod(period)
    }

    override fun toString(): String {
        return "Macbook(coupangId=$coupangId, company='$company', name='$name', chip='$chip', cpu='$cpu', gpu='$gpu', storage='$storage', memory='$memory', language='$language', color='$color', size=$size, releaseYear=$releaseYear, category=$category, productLink='$productLink', partnersLink='$partnersLink', thumbnail='$thumbnail')"
    }
}
