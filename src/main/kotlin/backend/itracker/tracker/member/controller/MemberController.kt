package backend.itracker.tracker.member.controller

import backend.itracker.tracker.common.response.SingleData
import backend.itracker.tracker.member.controller.response.MyInfoResponse
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.resolver.LoginMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {

    @GetMapping("/api/v1/me")
    fun getMyInfo(@LoginMember member: Member): ResponseEntity<SingleData<MyInfoResponse>> {
        return ResponseEntity.ok(SingleData(MyInfoResponse.from(member)))
    }
}
