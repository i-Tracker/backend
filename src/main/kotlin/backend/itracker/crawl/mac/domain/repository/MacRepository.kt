package backend.itracker.crawl.mac.domain.repository

import backend.itracker.crawl.mac.domain.Mac
import org.springframework.data.jpa.repository.JpaRepository

interface MacRepository : JpaRepository<Mac, Long> {
}
