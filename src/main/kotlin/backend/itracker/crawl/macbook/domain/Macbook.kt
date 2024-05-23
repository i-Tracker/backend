package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.crawl.common.ProductCategory
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "macbook")
class Macbook(
    val coupangId: Long,
    val company: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    val category: ProductCategory,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val language: String,
    val color: String,
    val size: Int,
    val releaseYear: Int,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: MacbookPrices = MacbookPrices(),

    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(targetPrices: MacbookPrices) {
        targetPrices.macbookPrices.forEach(this::addPrice)
    }

    fun addPrice(targetPrice: MacbookPrice) {
        prices.add(targetPrice)
        targetPrice.changeMacbook(this)
    }

    fun findCurrentPrice(): BigDecimal {
        return prices.findCurrentPrice()
    }

    fun findDiscountPercentage(): Int {
        return prices.findTodayDiscountPercentage()
    }

    override fun toString(): String {
        return "Macbook(thumbnail='$thumbnail', productLink='$productLink', releaseYear=$releaseYear, size=$size, color='$color', language='$language', memory='$memory', storage='$storage', gpu='$gpu', cpu='$cpu', chip='$chip', category=$category, name='$name', company='$company', coupangId=$coupangId)"
    }
}
