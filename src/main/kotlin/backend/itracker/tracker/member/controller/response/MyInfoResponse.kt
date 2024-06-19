package backend.itracker.tracker.member.controller.response

import backend.itracker.tracker.oauth.Member

data class MyInfoResponse(
    val id: Long,
    val nickname: String,
    val image: String,
) {

    companion object {
        fun from(member: Member): MyInfoResponse {
            return MyInfoResponse(
                id = member.id,
                nickname = member.nickname,
                image = member.profileImage
            )
        }
    }
}
