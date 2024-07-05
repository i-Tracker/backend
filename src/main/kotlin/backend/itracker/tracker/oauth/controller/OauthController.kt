package backend.itracker.tracker.oauth.controller

import backend.itracker.tracker.common.response.SingleData
import backend.itracker.tracker.oauth.OauthServerType
import backend.itracker.tracker.oauth.RedirectType
import backend.itracker.tracker.oauth.controller.response.PhoneNumberValidateResponse
import backend.itracker.tracker.oauth.service.OauthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OauthController(
    private val oauthService: OauthService
) {

    @GetMapping("/api/v1/oauth/{oauthServerType}")
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

    @GetMapping("/api/v1/oauth/login/{oauthServerType}")
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

    @GetMapping("/api/v1/login/validate")
    fun validatePhoneNumber(
        @RequestParam("phoneNumber") phoneNumber: String,
    ): ResponseEntity<SingleData<PhoneNumberValidateResponse>> {
        val isDuplicatedPhoneNumber = oauthService.validatePhoneNumber(phoneNumber)

        return ResponseEntity.ok(SingleData(PhoneNumberValidateResponse(isDuplicatedPhoneNumber)))
    }
}
