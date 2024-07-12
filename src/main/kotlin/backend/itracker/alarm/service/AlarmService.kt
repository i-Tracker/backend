package backend.itracker.alarm.service

import backend.itracker.alarm.infra.slack.Color
import backend.itracker.alarm.infra.slack.SlackClient
import backend.itracker.alarm.service.event.FailedNotificationEvent
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.schedule.service.event.CrawlEndEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private const val CRAWL_RESULT_TITLE = "*[ 크롤링 결과 ]*"

@Component
class AlarmService (
    private val slackClient: SlackClient,
    private val crawlResultService: CrawlResultService,
){

    @EventListener(CrawlEndEvent::class)
    fun alarmCrawlEndEvent() {
        val crawlResult = crawlResultService.findRecentCrawlResult()
        val messages = listOf(
            "*Macbook* : ${crawlResult.alreadyCrawlMacbook()}",
            "*Mac* : ${crawlResult.alreadyCrawlMac()}",
            "*Iphone* : ${crawlResult.alreadyCrawlIphone()}",
            "*Ipad* : ${crawlResult.alreadyCrawlIpad()}",
            "*AppleWatch* : ${crawlResult.alreadyCrawlAppleWatch()}",
            "*AirPods* : ${crawlResult.alreadyCrawlAirpods()}",
        )
        when (crawlResult.hasError()) {
            true -> slackClient.sendNotification(CRAWL_RESULT_TITLE, Color.RED, messages)
            false -> slackClient.sendNotification(CRAWL_RESULT_TITLE, Color.GREEN, messages)
        }
    }

    @EventListener(FailedNotificationEvent::class)
    fun alarmNotificationFailEvent(event: FailedNotificationEvent) {
        slackClient.sendNotification(
            """
            |*[ 알림 전송 실패 ]*
            |*실패 사유* : ${event.getCause()}
            """.trimMargin(),
            Color.RED,
            event.getMessages()
        )
    }
}
