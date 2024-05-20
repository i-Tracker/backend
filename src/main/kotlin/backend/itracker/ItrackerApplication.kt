package backend.itracker

import backend.itracker.config.AuditConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(AuditConfig::class)
@SpringBootApplication
class ItrackerApplication

fun main(args: Array<String>) {
	runApplication<ItrackerApplication>(*args)
}
