package backend.itracker.logging.aspect

import com.google.common.net.HttpHeaders
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.MDC
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*


private val LOGGER = KotlinLogging.logger {}
private val IP_HEADERS = listOf(
    HttpHeaders.X_FORWARDED_FOR,
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_CLIENT_IP",
    "HTTP_X_FORWARDED_FOR"
)

@Profile("!test")
@Component
@Aspect
class LoggerAspect {

    @Pointcut(
        """ @annotation(org.springframework.web.bind.annotation.PostMapping) ||
                @annotation(org.springframework.web.bind.annotation.GetMapping) ||
                @annotation(org.springframework.web.bind.annotation.PatchMapping) ||
                @annotation(org.springframework.web.bind.annotation.PutMapping) ||
                @annotation(org.springframework.web.bind.annotation.DeleteMapping)"""
    )
    fun apiInfo() {
    }

    @Before("apiInfo()")
    fun beforeApiRequest(joinPoint: JoinPoint) {
        val request =
            (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()) as ServletRequestAttributes).request
        LOGGER.info {
            """
            [ API Start ]
            - Request ID: ${getRequestId()}
            - Method: ${request.method}
            - URI: ${getURI(request)}
            - IP: ${getClientIP(request)}
            - Signature: ${getSignature(joinPoint)}
        """.trimIndent()
        }
    }

    @AfterReturning(value = "apiInfo()", returning = "response")
    fun afterApiRequest(response: Any) {
        LOGGER.info {
            """
            [ API End ]
            - Request ID: ${getRequestId()}
            - Response: $response
        """.trimIndent()
        }
    }

    private fun getRequestId(): String {
        return MDC.get("request_id")
    }

    private fun getURI(request: HttpServletRequest): String {
        return "${request.requestURI}${request.queryString.let { if (it != null) "?$it" else "" }}"
    }

    private fun getClientIP(request: HttpServletRequest): String? {
        return IP_HEADERS.firstNotNullOfOrNull { header -> request.getHeader(header) } ?: request.remoteAddr
    }

    private fun getSignature(joinPoint: JoinPoint): String {
        return "${joinPoint.signature.declaringType.simpleName}.${joinPoint.signature.name}"
    }
}
