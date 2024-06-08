package backend.itracker.crawl.iphone.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "iphone")
class Iphone(
    val coupangId: Long,
    val company: String,
    val generation: Int,
    val color: String,
    val storage: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Enumerated(EnumType.STRING)
    val category: IphoneCategory,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: IphonePrices = IphonePrices(),

    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(prices: IphonePrices) {
        prices.iphonePrices.forEach(this::addPrice)
    }

    fun addPrice(targetPrice: IphonePrice) {
        prices.add(targetPrice)
        targetPrice.changeIphone(this)
    }

    override fun toString(): String {
        return "Iphone(coupangId=$coupangId, company='$company', generation='$generation', color='$color', storage='$storage', name='$name', category=$category, productLink='$productLink', thumbnail='$thumbnail')"
    }
}
