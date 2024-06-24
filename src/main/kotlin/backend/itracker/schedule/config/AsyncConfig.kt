package backend.itracker.schedule.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

private const val THREAD_POOL_SIZE = 2
private const val MAX_POOL_SIZE = 4
private const val QUEUE_CAPACITY = 4

@EnableAsync
@Configuration
class AsyncConfig {

    @Bean
    fun taskExecutor(): TaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.setThreadNamePrefix("Async-itracker-")
        taskExecutor.corePoolSize = THREAD_POOL_SIZE
        taskExecutor.maxPoolSize = MAX_POOL_SIZE
        taskExecutor.queueCapacity = QUEUE_CAPACITY
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true)
        taskExecutor.setAwaitTerminationSeconds(180)
        return taskExecutor
    }
}
