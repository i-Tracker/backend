package backend.itracker.crawl.iphone.domain.repository

import backend.itracker.crawl.iphone.domain.Iphone
import org.springframework.data.jpa.repository.JpaRepository

interface IphoneRepository : JpaRepository<Iphone, Long>

