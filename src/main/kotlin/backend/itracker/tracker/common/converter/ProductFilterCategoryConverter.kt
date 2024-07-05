package backend.itracker.tracker.common.converter

import backend.itracker.crawl.common.ProductFilterCategory
import org.springframework.core.convert.converter.Converter

private val productFilterCategories = ProductFilterCategory.entries

class ProductFilterCategoryConverter : Converter<String, ProductFilterCategory> {

    override fun convert(source: String): ProductFilterCategory {
        return productFilterCategories.firstOrNull { it.name.lowercase() == source }
        ?: throw IllegalArgumentException("지원하지 않는 카테고리 입니다. category: $source")
    }
}
