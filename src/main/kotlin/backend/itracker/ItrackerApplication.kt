package backend.itracker

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItrackerApplication

fun main(args: Array<String>) {
	runApplication<ItrackerApplication>(*args)
}
