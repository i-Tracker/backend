package backend.itracker.crawl.macbook.domain

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.crawl.common.ProductCategory
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

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

    @OneToMany(mappedBy = "macbook", cascade = [CascadeType.PERSIST])
    val prices: MutableList<MacbookPrice> = mutableListOf(),

    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(macbookPrices: List<MacbookPrice>) {
        macbookPrices.forEach { addPrice(it) }
    }

    fun addPrice(macbookPrice: MacbookPrice) {
        prices.add(macbookPrice)
        macbookPrice.changeMacbook(this)
    }

    fun keepOnlyRecentPrice() {
        val recentPrice = prices.maxBy { it.createdAt }
        prices.clear()
        prices.add(recentPrice)
    }

    override fun toString(): String {
        return "Macbook(thumbnail='$thumbnail', productLink='$productLink', releaseYear=$releaseYear, size=$size, color='$color', language='$language', memory='$memory', storage='$storage', gpu='$gpu', cpu='$cpu', chip='$chip', category=$category, name='$name', company='$company', coupangId=$coupangId)"
    }
}
