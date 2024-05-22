package backend.itracker.tracker.controller.response

import backend.itracker.crawl.common.ProductCategory
import kotlin.enums.EnumEntries

data class CategoryResponses(
    val categories: List<String>
) {

    constructor(categories: EnumEntries<ProductCategory>) : this(categories = categories.map { it.name.lowercase() })
}
