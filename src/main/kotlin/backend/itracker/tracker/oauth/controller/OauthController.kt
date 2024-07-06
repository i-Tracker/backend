package backend.itracker.tracker.oauth.controller

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.service.OauthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
            val member = oauthService.login(oauthServerType, code, RedirectType.LOCAL)
            val accessToken = oauthService.issueToken(member)

            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
        }

        val member = oauthService.login(oauthServerType, code, RedirectType.PROD)
        val accessToken = oauthService.issueToken(member)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .build()
    }

    @GetMapping("/login/{oauthServerType}/new")
    fun firstLogin(
        @PathVariable oauthServerType: OauthServerType,
        @RequestParam("code") code: String,
        request: HttpServletRequest,
    ): ResponseEntity<Long> {
        if (request.getHeader(HttpHeaders.REFERER).contains("localhost")) {
            val member = oauthService.firstLogin(oauthServerType, code, RedirectType.LOCAL)
            oauthService.register(member)
            val accessToken = oauthService.issueToken(member)

            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
        }

        val member = oauthService.firstLogin(oauthServerType, code, RedirectType.PROD)
        oauthService.register(member)
        val accessToken = oauthService.issueToken(member)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .build()
    }
}
