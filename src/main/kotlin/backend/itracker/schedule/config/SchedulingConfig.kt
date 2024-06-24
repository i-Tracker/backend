package backend.itracker.schedule.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
class SchedulingConfig {

    @Bean
    fun taskScheduler(): TaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = 1
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduling-itracker-")
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true)
        threadPoolTaskScheduler.setAwaitTerminationSeconds(180)
        return threadPoolTaskScheduler
    }
}

