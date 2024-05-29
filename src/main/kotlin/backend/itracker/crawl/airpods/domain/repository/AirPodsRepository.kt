package backend.itracker.crawl.airpods.domain.repository

import backend.itracker.crawl.airpods.domain.AirPods
import org.springframework.data.jpa.repository.JpaRepository

interface AirPodsRepository : JpaRepository<AirPods, Long> {
}
