package backend.itracker.crawl.macbook.fixtures

import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition

abstract class MacbookFilterConditionFixture {

    companion object {
        fun create(
            size: Int? = null,
            color: String? = null,
            processor: String? = null,
            storage: String? = null,
            memory: String? = null
        ): MacbookFilterCondition {
            return MacbookFilterCondition(
                size = size,
                color = color,
                processor = processor,
                storage = storage,
                memory = memory
            )
        }
    }
}
