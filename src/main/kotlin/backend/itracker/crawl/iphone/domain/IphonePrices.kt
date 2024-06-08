package backend.itracker.crawl.iphone.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class IphonePrices(
    @OneToMany(mappedBy = "iphone", cascade = [CascadeType.PERSIST])
    val iphonePrices: MutableList<IphonePrice> = mutableListOf()
) {

    fun add(iphonePrice: IphonePrice) {
        iphonePrices.add(iphonePrice)
    }
}
