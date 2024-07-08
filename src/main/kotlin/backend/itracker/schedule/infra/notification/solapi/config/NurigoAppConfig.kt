package backend.itracker.schedule.infra.notification.solapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@Profile("!test")
@ConfigurationProperties(prefix = "nurigo.message")
data class NurigoAppConfig(
    val apiKey: String,
    val apiSecretKey: String,
    val domain: String,
)
