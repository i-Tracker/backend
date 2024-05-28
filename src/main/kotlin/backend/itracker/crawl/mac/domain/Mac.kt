package backend.itracker.crawl.mac.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table


@Entity
@Table(name = "mac")
class Mac(
    val coupangId: Long,
    val company: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Enumerated(EnumType.STRING)
    val category: MacCategory,

    val releaseYear: Int,
    val size: String,
    var color: String,
    var chip: String,
    var cpu: String,
    var gpu: String,
    var memory: String,
    var storage: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: MacPrices = MacPrices(),

    id: Long = 0L
) :BaseEntity(id){

    fun addAllPrices(prices: MacPrices) {
        prices.macPrices.forEach(this::addPrice)
    }

    fun addPrice(targetPrice: MacPrice) {
        prices.add(targetPrice)
        targetPrice.changeMac(this)
    }

    override fun toString(): String {
        return "Mac(coupangId=$coupangId, company='$company', name='$name', category=$category, releaseYear=$releaseYear, size=$size, color='$color', chip='$chip', cpu='$cpu', gpu='$gpu', memory='$memory', storage='$storage', productLink='$productLink', thumbnail='$thumbnail')"
    }
}
