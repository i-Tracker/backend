package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.domain.QMacbook.macbook
import backend.itracker.crawl.macbook.domain.QMacbookPrice.macbookPrice
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory

class MacbookRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : MacbookRepositoryCustom {

    override fun findAllByFilterCondition(
        category: MacbookCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook> {
        return jpaQueryFactory
            .selectFrom(macbook)
            .where(
                equalSize(filterCondition.size),
                equalColor(filterCondition.color),
                equalChip(filterCondition.processor),
                equalStorage(filterCondition.storage),
                equalMemory(filterCondition.memory),
                equalCategory(category)
            ).fetch()
    }

    override fun findAllFetchBySearchCondition(
        category: MacbookCategory,
        filterCondition: MacbookFilterCondition
    ): List<Macbook> {
        return jpaQueryFactory
            .selectFrom(macbook)
            .join(macbook.prices.macbookPrices, macbookPrice).fetchJoin()
            .where(
                equalSize(filterCondition.size),
                equalColor(filterCondition.color),
                equalChip(filterCondition.processor),
                equalStorage(filterCondition.storage),
                equalMemory(filterCondition.memory),
                equalCategory(category)
            ).fetch()
    }

    private fun equalSize(
        size: Int?
    ): Predicate? {
        return size?.let {
            macbook.size.eq(size)
        }
    }

    private fun equalColor(
        color: String?
    ): Predicate? {
        return color?.let {
            macbook.color.eq(color)
        }
    }

    private fun equalChip(
        chip: String?
    ): Predicate? {
        return chip?.let {
            macbook.chip.eq(chip)
        }
    }

    private fun equalStorage(
        storage: String?
    ): Predicate? {
        return storage?.let {
            macbook.storage.eq(storage)
        }
    }

    private fun equalMemory(
        memory: String?
    ): Predicate? {
        return memory?.let {
            macbook.memory.eq(memory)
        }
    }

    private fun equalCategory(
        category: MacbookCategory
    ): Predicate? {
        return macbook.category.eq(category)
    }
}
