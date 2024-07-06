package backend.itracker.tracker.oauth.controller

import backend.itracker.tracker.common.response.SingleData
import backend.itracker.tracker.oauth.controller.response.PhoneNumberValidateResponse
import backend.itracker.tracker.oauth.service.OauthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class LoginValidateController(
    private val oauthService: OauthService,
) {

    @GetMapping("/login/validate")
    fun validatePhoneNumber(
        @RequestParam("phoneNumber") phoneNumber: String,
    ): ResponseEntity<SingleData<PhoneNumberValidateResponse>> {
        val isDuplicatedPhoneNumber = oauthService.validatePhoneNumber(phoneNumber)

        return ResponseEntity.ok(SingleData(PhoneNumberValidateResponse(isDuplicatedPhoneNumber)))
    }
}
