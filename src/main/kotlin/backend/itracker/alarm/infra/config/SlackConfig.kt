package backend.itracker.alarm.infra.config

import com.slack.api.Slack
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackConfig {

    @Bean
    fun slack(): Slack {
        return Slack.getInstance()
    }
}
