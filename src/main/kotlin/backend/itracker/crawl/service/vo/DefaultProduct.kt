package backend.itracker.crawl.service.vo

data class DefaultProduct(
    val productId: Long,
    val subCategory: String,
    val name: String,
    val price: DefaultPrice,
    val productLink: String,
    val thumbnailLink: String
) {

    constructor(
        productId: Long,
        subCategory: String,
        names: List<String>,
        priceInfo: DefaultPrice,
        productLink: String,
        thumbnailLink: String,
    ) : this(
        productId = productId,
        subCategory = subCategory,
        name = names[0],
        price = priceInfo,
        productLink = productLink,
        thumbnailLink = thumbnailLink
    )
}
