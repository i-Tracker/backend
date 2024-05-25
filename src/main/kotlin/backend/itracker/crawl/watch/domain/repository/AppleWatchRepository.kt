package backend.itracker.crawl.watch.domain.repository

import backend.itracker.crawl.watch.domain.AppleWatch
import org.springframework.data.jpa.repository.JpaRepository

interface AppleWatchRepository : JpaRepository<AppleWatch, Long>
