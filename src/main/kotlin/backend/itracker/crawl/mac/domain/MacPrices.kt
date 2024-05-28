package backend.itracker.crawl.mac.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class MacPrices(
    @OneToMany(mappedBy = "mac", cascade = [CascadeType.PERSIST])
    val macPrices: MutableList<MacPrice> = mutableListOf()
) {

    fun add(targetPrice: MacPrice) {
        macPrices.add(targetPrice)
    }
}
