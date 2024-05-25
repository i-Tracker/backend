package backend.itracker.crawl.ipad.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "ipad")
class Ipad(
    val coupangId: Long,
    val company: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Enumerated(EnumType.STRING)
    val category: IpadCategory,

    val chip: String,
    val storage: String,
    val color: String,
    val size: String,
    val releaseYear: Int,
    val isCellular: Boolean,
    val generation: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: IpadPrices = IpadPrices(),

    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(targetPrices: IpadPrices) {
        targetPrices.ipadPrices.forEach(this::addPrice)
    }

    fun addPrice(ipadPrice: IpadPrice) {
        prices.add(ipadPrice)
        ipadPrice.changeIpad(this)
    }

    override fun toString(): String {
        return "Ipad(thumbnail='$thumbnail', productLink='$productLink', generation='$generation', isCellular=$isCellular, releaseYear=$releaseYear, size=$size, color='$color', storage='$storage', chip='$chip', name='$name', company='$company', coupangId=$coupangId)"
    }
}
