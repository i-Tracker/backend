package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition

interface MacbookRepositoryCustom {

    fun findAllByFilterCondition(
        category: MacbookCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook>

    fun findAllFetchBySearchCondition(
        category: MacbookCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook>
}
