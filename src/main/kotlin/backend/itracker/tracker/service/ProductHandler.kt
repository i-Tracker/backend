package backend.itracker.tracker.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.response.CommonProductModel


interface ProductHandler {

    fun supports(productCategory: ProductCategory): Boolean

    fun findTopDiscountPercentageProducts(productCategory: ProductCategory, limit: Int): List<CommonProductModel>
}
