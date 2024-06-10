package backend.itracker.crawl.airpods.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Period

@Entity
@Table(name = "airpods")
class AirPods(
    val coupangId: Long,
    val company: String,
    val releaseYear: Int,
    val generation: Int,
    val canWirelessCharging: Boolean,
    val chargingType: String,
    val color: String,

    @Enumerated(EnumType.STRING)
    val category: AirPodsCategory,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    var partnersLink: String = "",

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: AirPodsPrices = AirPodsPrices(),

    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(prices: AirPodsPrices) {
        prices.airPodsPrices.forEach(this::addPrice)
    }

    fun addPrice(airPodsPrice: AirPodsPrice) {
        prices.add(airPodsPrice)
        airPodsPrice.changeAirPods(this)
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

    fun changePartnersLink(partnersLink: String) {
        this.partnersLink = partnersLink
    }

    fun getRecentPricesByPeriod(period: Period): AirPodsPrices {
        return prices.getRecentPricesByPeriod(period)
    }

    override fun toString(): String {
        return "AirPods(coupangId=$coupangId, company='$company', releaseYear=$releaseYear, generation=$generation, canWirelessCharging=$canWirelessCharging, chargingType='$chargingType', color='$color', category=$category, name='$name', productLink='$productLink', partnersLink='$partnersLink', thumbnail='$thumbnail')"
    }
}
