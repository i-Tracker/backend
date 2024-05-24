package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition

interface MacbookRepositoryCustom {

    fun findAllByFilterCondition(
        productCategory: ProductCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook>
}
