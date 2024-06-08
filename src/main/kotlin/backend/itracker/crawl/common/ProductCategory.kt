package backend.itracker.crawl.common

import backend.itracker.crawl.macbook.domain.MacbookCategory

enum class ProductCategory {
    MACBOOK_AIR,
    MACBOOK_PRO,
    MAC,
    I_PHONE,
    I_PAD,
    AIRPODS,
    APPLE_WATCH;

    fun toMacbookCategory(): MacbookCategory {
        return when (this) {
            MACBOOK_AIR -> MacbookCategory.MACBOOK_AIR
            MACBOOK_PRO -> MacbookCategory.MACBOOK_PRO
            else -> throw IllegalArgumentException("MacbookCategory로 변환할 수 없는 ProductCategory 입니다. category: $this")
        }
    }
}
