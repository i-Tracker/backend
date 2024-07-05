package backend.itracker.tracker.product.response

import backend.itracker.crawl.common.ProductFilterCategory
import kotlin.enums.EnumEntries

data class CategoryResponses(
    val categories: List<String>
) {

    constructor(categories: EnumEntries<ProductFilterCategory>) : this(categories = categories.map { it.name.lowercase() })
}
