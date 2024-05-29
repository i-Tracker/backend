package backend.itracker.crawl.airpods.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class AirPodsPrices(
    @OneToMany(mappedBy = "airPods", cascade = [CascadeType.PERSIST])
    val airPodsPrices: MutableList<AirPodsPrice> = mutableListOf()
) {

    fun add(targetAirPodsPrice: AirPodsPrice) {
        airPodsPrices.add(targetAirPodsPrice)
    }
}
