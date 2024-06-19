package backend.itracker.tracker.common.converter

import backend.itracker.crawl.common.ProductCategory
import org.springframework.core.convert.converter.Converter

private val productCategories = ProductCategory.entries

class ProductCategoryConverter : Converter<String, ProductCategory> {

    override fun convert(source: String): ProductCategory {
        return productCategories.firstOrNull { it.name.lowercase() == source }
        ?: throw IllegalArgumentException("지원하지 않는 카테고리 입니다. category: $source")
    }
}
