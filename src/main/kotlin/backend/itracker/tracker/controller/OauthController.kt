package backend.itracker.tracker.controller

import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.service.oauth.OauthService
import jakarta.servlet.http.HttpServletResponse
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
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        oauthService.getAuthCodeRequestUrl(oauthServerType).let {
            response.sendRedirect(it)
        }

        return ResponseEntity.status(HttpStatus.FOUND).build()
    }

    @GetMapping("/login/{oauthServerType}")
    fun login(
        @PathVariable oauthServerType: OauthServerType,
        @RequestParam("code") code: String
    ): ResponseEntity<Long> {
        val login = oauthService.login(oauthServerType, code)
        return ResponseEntity.ok(login)
    }
}
