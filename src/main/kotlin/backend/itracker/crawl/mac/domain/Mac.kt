package backend.itracker.crawl.mac.domain

import backend.itracker.crawl.common.BaseEntity
import backend.itracker.crawl.ipad.domain.IpadCategory
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
    val category: IpadCategory,

    val releaseYear: Int,
    val size: Int,
    var color: String,
    var chip: String,
    var cpu: String,
    var gpu: String,
    var memeory: String,
    var storage: String,

    @Column(columnDefinition = "TEXT")
    val productLink: String,

    @Column(columnDefinition = "TEXT")
    val thumbnail: String,

    @Embedded
    val prices: MacPrices = MacPrices(),

    id: Long = 0L
) :BaseEntity(id){

    override fun toString(): String {
        return "Mac(thumbnail='$thumbnail', productLink='$productLink', storage='$storage', memeory='$memeory', gpu='$gpu', cpu='$cpu', chip='$chip', color='$color', size=$size, releaseYear=$releaseYear, category=$category, name='$name', company='$company', coupangId=$coupangId)"
    }
}
