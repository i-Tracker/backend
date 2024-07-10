package backend.itracker.logging.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.*

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val requestId = (request as HttpServletRequest).getHeader("X-Request-ID")
        MDC.put("request_id", requestId ?: UUID.randomUUID().toString())

        chain!!.doFilter(request, response)

        MDC.clear()
    }
}
