package backend.itracker.tracker.oauth.controller

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.service.LoginFacade
import backend.itracker.tracker.oauth.service.OauthService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger { }
@RestController
@RequestMapping("/api/v1/oauth")
class OauthController(
    private val loginFacade: LoginFacade,
    private val oauthService: OauthService
) {

    @GetMapping("/{oauthServerType}")
    fun redirectAuthCodeRequestUrl(
        @PathVariable oauthServerType: OauthServerType,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        if (request.getHeader(HttpHeaders.REFERER).contains("localhost")) {
            logger.info { "${MDC.get("request_id")} -> code local" }
            oauthService.getAuthCodeRequestUrl(oauthServerType, RedirectType.LOCAL).let {
                response.sendRedirect(it)
            }
            return ResponseEntity.status(HttpStatus.FOUND).build()
        }
        logger.info { "${MDC.get("request_id")} -> code prod" }
        oauthService.getAuthCodeRequestUrl(oauthServerType, RedirectType.PROD).let {
            response.sendRedirect(it)
        }

        return ResponseEntity.status(HttpStatus.FOUND).build()
    }

    @GetMapping("/login/{oauthServerType}")
    fun login(
        @PathVariable oauthServerType: OauthServerType,
        @RequestParam("code") code: String,
        request: HttpServletRequest,
    ): ResponseEntity<Long> {
        if (request.getHeader(HttpHeaders.REFERER).contains("localhost")) {
            logger.info { "${MDC.get("request_id")} -> token local" }
            val accessToken = loginFacade.login(oauthServerType, code, RedirectType.LOCAL)

            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
        }

        logger.info { "${MDC.get("request_id")} -> token prod" }
        val accessToken = loginFacade.login(oauthServerType, code, RedirectType.PROD)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .build()
    }
}
