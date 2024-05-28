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

    fun isMacBook(): Boolean {
        return name.contains("맥북") && !name.contains("정품")
    }

    fun isIpad(): Boolean {
        return name.contains("아이패드")
    }

    fun isAppleWatch(): Boolean {
        return name.contains("애플워치") && !name.contains("정품")
    }

    fun isMac(): Boolean {
        return name.contains("아이맥") ||
                name.contains("맥스튜디오") ||
                name.contains("맥 미니") ||
                name.contains("맥미니") ||
                name.contains("Mac mini")
    }
}
