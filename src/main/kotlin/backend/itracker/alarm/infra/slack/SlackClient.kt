package backend.itracker.alarm.infra.slack

import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.webhook.Payload
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SlackClient(private val slack: Slack) {

    @Value("\${slack.web-hook.url}")
    private lateinit var webhookUrl: String

    fun sendNotification(title: String, color: Color, messages: List<String>) {
        slack.send(
            webhookUrl, Payload.builder().text(title).attachments(
                listOf(
                    Attachment.builder()
                        .color(color.value)
                        .blocks(
                            messages.map { getSectionBlock(it) }
                        ).build()
                )
            ).build()
        )
    }

    private fun getSectionBlock(
        text: String,
    ): SectionBlock {
        return SectionBlock.builder().text(
            MarkdownTextObject.builder().text(text).build()
        ).build()
    }
}
