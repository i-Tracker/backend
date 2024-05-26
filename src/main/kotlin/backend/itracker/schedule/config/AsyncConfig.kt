package backend.itracker.schedule.config

import ch.qos.logback.core.CoreConstants.MAX_POOL_SIZE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

private const val THREAD_POOL_SIZE = 2
private const val MAX_POOL_SIZE = 4
private const val QUEUE_CAPACITY = 4

@EnableAsync
@Configuration
class AsyncConfig {

    @Bean
    fun taskExecutor(): Executor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.setThreadNamePrefix("Async-Schedule-")
        taskExecutor.corePoolSize = THREAD_POOL_SIZE
        taskExecutor.maxPoolSize = MAX_POOL_SIZE
        taskExecutor.queueCapacity = QUEUE_CAPACITY
        taskExecutor.initialize()
        return taskExecutor
    }
}
