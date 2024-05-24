package backend.itracker.tracker.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.product.CommonProductModel


interface ProductHandler {

    fun supports(productCategory: ProductCategory): Boolean

    fun findTopDiscountPercentageProducts(productCategory: ProductCategory, limit: Int): List<CommonProductModel>

    fun findFilter(productCategory: ProductCategory): CommonFilterModel
}
