package backend.itracker.crawl.airpods.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "airpods")
class AirPods(
    val coupangId: Long,
    val company: String,
    val releaseYear: Int,
    val generation: Int,
    val canWirelessCharging: Boolean,
    val chargingType: String,

    @Enumerated(EnumType.STRING)
    val category: AirPodsCategory,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: AirPodsPrices = AirPodsPrices(),

    id: Long = 0L
) {

    fun addAllPrices(prices: AirPodsPrices) {
        prices.airPodsPrices.forEach(this::addPrice)
    }

    fun addPrice(airPodsPrice: AirPodsPrice) {
        prices.add(airPodsPrice)
        airPodsPrice.changeAirPods(this)
    }

    override fun toString(): String {
        return "AirPods(coupangId=$coupangId, company='$company', releaseYear=$releaseYear, generation=$generation, canWirelessCharging=$canWirelessCharging, chargingType='$chargingType', category=$category, name='$name', productLink='$productLink', thumbnail='$thumbnail')"
    }
}
