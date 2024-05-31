package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface MacbookRepositoryCustom {

    fun findAllByFilterCondition(
        productCategory: ProductCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook>

    fun findAllProductsByFilter(
        category: ProductCategory,
        filterCondition: MacbookFilterCondition,
        pageable: Pageable
    ): PageImpl<Macbook>
}
