package backend.itracker.tracker.config

import backend.itracker.tracker.infra.oauth.kakao.client.KakaoApiClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class HttpInterfaceConfig {

    @Bean
    fun kakaoApiClient(): KakaoApiClient {
        val client = WebClient.create()
        val factory = HttpServiceProxyFactory.builder()
            .exchangeAdapter(WebClientAdapter.create(client))
            .build()

        return factory.createClient(KakaoApiClient::class.java)
    }
}
