package backend.itracker.crawl.watch.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "apple_watch")
class AppleWatch(
    val coupangId: Long,
    val company: String,

    @Column(columnDefinition = "TEXT")
    val name: String,

    val releaseYear: Int,
    val category: AppleWatchCategory,
    val caseType: String,
    val size: Int,
    val color: String,
    val band: String,
    val bandSize: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: AppleWatchPrices = AppleWatchPrices(),

    id: Long = 0L
) : BaseEntity(id) {

    override fun toString(): String {
        return "AppleWatch(thumbnail='$thumbnail', productLink='$productLink', bandSize='$bandSize', band='$band', color='$color', size=$size, caseType='$caseType', category=$category, releaseYear=$releaseYear, name='$name', company='$company', coupangId=$coupangId)"
    }
}
