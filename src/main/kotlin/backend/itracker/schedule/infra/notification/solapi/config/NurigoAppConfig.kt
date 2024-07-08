package backend.itracker.schedule.infra.notification.solapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "nurigo.message")
data class NurigoAppConfig(
    val apiKey: String,
    val apiSecretKey: String,
    val domain: String,
)
