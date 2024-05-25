package backend.itracker.crawl.watch.domain

import backend.itracker.crawl.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "apple_watch_price")
class AppleWatchPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean,
    id: Long = 0L
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "apple_watch_id", nullable = false, foreignKey = ForeignKey(name = "fk_apple_watch_price_apple_watch_id_ref_apple_watch_id")
    )
    var appleWatch: AppleWatch? = null

    fun changeAppleWatch(appleWatch: AppleWatch) {
        this.appleWatch = appleWatch
    }

    override fun toString(): String {
        return "AppleWatchPrice(isOutOfStock=$isOutOfStock, currentPrice=$currentPrice, basePrice=$basePrice, discountPercentage=$discountPercentage)"
    }
}
