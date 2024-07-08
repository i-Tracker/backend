package backend.itracker.schedule.infra.notification.solapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "nurigo.kakao.channel")
data class NurigoKakaoChannelConfig(
    val pfId: String,
    val priceChangeNotificationTemplateId: String,
    val primaryPhoneNumber: String,
    val sendingHour: Int,
)
