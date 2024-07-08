package backend.itracker.schedule.infra.notification.solapi.config

import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!test")
@Configuration
class NurigoConfig(
    private val nurigoAppConfig: NurigoAppConfig,
) {

    @Bean
    fun defaultMessageService(): DefaultMessageService {
        return NurigoApp.initialize(
            apiKey = nurigoAppConfig.apiKey,
            apiSecretKey = nurigoAppConfig.apiSecretKey,
            domain = nurigoAppConfig.domain
        )
    }
}
