package backend.itracker.tracker.controller

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.service.oauth.OauthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/oauth")
class OauthController(
    private val oauthService: OauthService
) {

    @GetMapping("/{oauthServerType}")
    fun redirectAuthCodeRequestUrl(
        @PathVariable oauthServerType: OauthServerType,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        if (request.getHeader(HttpHeaders.REFERER).contains("localhost")) {
            oauthService.getAuthCodeRequestUrl(oauthServerType, RedirectType.LOCAL).let {
                response.sendRedirect(it)
            }
            return ResponseEntity.status(HttpStatus.FOUND).build()
        }
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
            val accessToken = oauthService.login(oauthServerType, code, RedirectType.LOCAL)

            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
        }
        val accessToken = oauthService.login(oauthServerType, code, RedirectType.PROD)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .build()
    }
}
