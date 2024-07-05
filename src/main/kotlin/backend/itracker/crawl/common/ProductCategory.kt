package backend.itracker.crawl.common

enum class ProductCategory {
    MACBOOK,
    MAC,
    I_PHONE,
    I_PAD,
    AIRPODS,
    APPLE_WATCH;

    companion object {
        fun from(productFilterCategory: ProductFilterCategory): ProductCategory {
            return when (productFilterCategory) {
                ProductFilterCategory.MACBOOK_AIR,
                ProductFilterCategory.MACBOOK_PRO -> MACBOOK
                ProductFilterCategory.MAC -> MAC
                ProductFilterCategory.I_PHONE -> I_PHONE
                ProductFilterCategory.I_PAD -> I_PAD
                ProductFilterCategory.AIRPODS -> AIRPODS
                ProductFilterCategory.APPLE_WATCH -> APPLE_WATCH
            }
        }
    }
}
