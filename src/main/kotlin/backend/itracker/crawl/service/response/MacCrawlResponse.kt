package backend.itracker.crawl.service.response

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.MacCategory
import backend.itracker.crawl.mac.domain.MacPrice
import java.math.BigDecimal

data class MacCrawlResponse(
    val coupangId: Long,
    val company: String,
    val name: String,
    val category: MacCategory,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val color: String,
    val size: String,
    val releaseYear: Int,
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
    val productLink: String,
    val thumbnail: String,
    val isOutOfStock: Boolean
) {
    constructor(
        coupangId: Long,
        company: String,
        name: String,
        category: MacCategory,
        chip: String,
        cpu: String,
        gpu: String,
        storage: String,
        memory: String,
        color: String,
        size: String,
        releaseYear: String,
        discountPercentage: Int,
        basePrice: BigDecimal,
        discountPrice: BigDecimal,
        productLink: String,
        thumbnail: String,
        isOutOfStock: Boolean
    ) : this(
        coupangId,
        company,
        name,
        category,
        chip,
        cpu,
        gpu,
        storage,
        memory,
        color,
        size,
        releaseYear.toInt(),
        discountPercentage,
        basePrice,
        discountPrice,
        productLink,
        thumbnail,
        isOutOfStock,
    )

    fun toDomain(): Mac {
        val mac = Mac(
            coupangId = coupangId,
            company = company,
            name = name,
            category = category,
            chip = chip,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            color = color,
            size = size,
            releaseYear = releaseYear,
            productLink = productLink,
            thumbnail = thumbnail,
        )
        mac.addPrice(
            MacPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                currentPrice = discountPrice,
                isOutOfStock = isOutOfStock
            )
        )
        return mac
    }
}
