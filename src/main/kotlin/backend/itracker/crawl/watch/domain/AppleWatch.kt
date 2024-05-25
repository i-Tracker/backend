package backend.itracker.crawl.watch.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "apple_watch")
class AppleWatch(
    val coupangId: Long,
    val company: String,
    val releaseYear: Int,

    @Column(columnDefinition = "TEXT")
    val name: String,

    @Enumerated(EnumType.STRING)
    val category: AppleWatchCategory,

    val caseType: String,
    val size: Int,
    val color: String,
    val band: String,
    val bandSize: String,
    val isCellular: Boolean,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: AppleWatchPrices = AppleWatchPrices(),

    id: Long = 0L
) : BaseEntity(id) {

    fun addAllPrices(prices: AppleWatchPrices) {
        prices.appleWatchPrices.forEach(this::add)
    }

    fun add(appleWatchPrice: AppleWatchPrice) {
        prices.add(appleWatchPrice)
        appleWatchPrice.changeAppleWatch(this)
    }

    override fun toString(): String {
        return "AppleWatch(thumbnail='$thumbnail', productLink='$productLink', isCellular=$isCellular, bandSize='$bandSize', band='$band', color='$color', size=$size, caseType='$caseType', category=$category, releaseYear=$releaseYear, name='$name', company='$company', coupangId=$coupangId)"
    }
}
