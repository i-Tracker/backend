package backend.itracker.crawl.service.vo

data class DefaultProduct(
    val productId: Long,
    val category: String,
    val name: String,
    val price: DefaultPrice,
    val productLink: String,
    val thumbnailLink: String
) {

    constructor(
        productId: Long,
        category: String,
        names: List<String>,
        priceInfo: DefaultPrice,
        productLink: String,
        thumbnailLink: String,
    ) : this(
        productId = productId,
        category = category,
        name = names[0],
        price = priceInfo,
        productLink = productLink,
        thumbnailLink = thumbnailLink
    )
}
