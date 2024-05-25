package backend.itracker.crawl.watch.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class AppleWatchPrices(
    @OneToMany(mappedBy = "appleWatch", cascade = [CascadeType.PERSIST])
    val appleWatchPrices: MutableList<AppleWatchPrice> = mutableListOf()
) {

    fun add(targetPrice: AppleWatchPrice) {
        appleWatchPrices.add(targetPrice)
    }
}
