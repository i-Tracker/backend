package backend.itracker.crawl.macbook.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MacbookRepository: JpaRepository<Macbook, Long> {

    fun findByName(name: String): Optional<Macbook>
}
