package backend.itracker.config

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.MacbookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@TestExecutionListeners(value = [DatabaseTruncater::class], mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@SpringBootTest
abstract class ServiceTestConfig : RepositoryTestConfig() {

    @Autowired
    lateinit var macbookService: MacbookService

    fun saveMacbook(macbook: Macbook) : Macbook {
        macbookRepository.save(macbook)
        return macbook
    }
}
