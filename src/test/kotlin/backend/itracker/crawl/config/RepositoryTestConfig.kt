package backend.itracker.crawl.config

import backend.itracker.crawl.macbook.domain.repository.MacbookRepository
import org.springframework.beans.factory.annotation.Autowired

abstract class RepositoryTestConfig {

    @Autowired
    lateinit var macbookRepository: MacbookRepository
}
