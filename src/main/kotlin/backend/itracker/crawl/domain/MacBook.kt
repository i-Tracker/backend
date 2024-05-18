package backend.itracker.crawl.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "macbook")
class MacBook(
    val company: String,
    val name: String,
    val type: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val language: String,
    val color: String,
    val size: Int,
    val releaseYear: Int,

    @OneToMany(cascade = [CascadeType.PERSIST])
    val price: MutableList<MacBookPrice> = mutableListOf(),

    val productLink: String,
    val thumbnail: String
) : BaseEntity() {

    override fun toString(): String {
        return "MacBook(company='$company', name='$name', type='$type', cpu='$cpu', gpu='$gpu', storage='$storage', memory='$memory', language='$language', color='$color', size=$size, releaseYear=$releaseYear, price=$price, productLink='$productLink', thumbnail='$thumbnail')"
    }
}
