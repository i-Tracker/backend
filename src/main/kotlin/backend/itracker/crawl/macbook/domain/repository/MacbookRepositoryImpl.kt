package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.QMacbook.macbook
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class MacbookRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : MacbookRepositoryCustom {

    override fun findAllByFilterCondition(
        productCategory: ProductCategory,
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
                equalCategory(productCategory)
            ).fetch()
    }

    override fun findAllProductsByFilter(
        category: ProductCategory,
        filterCondition: MacbookFilterCondition,
        pageable: Pageable
    ): PageImpl<Macbook> {
        val contents = jpaQueryFactory.selectFrom(macbook)
            .where(
                equalSize(filterCondition.size),
                equalColor(filterCondition.color),
                equalChip(filterCondition.processor),
                equalStorage(filterCondition.storage),
                equalMemory(filterCondition.memory),
                equalCategory(category)
            ).offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = jpaQueryFactory.selectFrom(macbook)
            .where(
                equalSize(filterCondition.size),
                equalColor(filterCondition.color),
                equalChip(filterCondition.processor),
                equalStorage(filterCondition.storage),
                equalMemory(filterCondition.memory),
                equalCategory(category)
            ).fetch()
            .count()
            .toLong()

        return PageImpl(contents, pageable, total)
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
        category: ProductCategory
    ): Predicate? {
        return macbook.category.eq(category)
    }
}
