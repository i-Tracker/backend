package backend.itracker.crawl.ipad.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class IpadPrices(
    @OneToMany(mappedBy = "ipad", cascade = [CascadeType.PERSIST])
    val ipadPrices: MutableList<IpadPrice> = mutableListOf()
) {

    fun add(ipadPrice: IpadPrice) {
        ipadPrices.add(ipadPrice)
    }
}
