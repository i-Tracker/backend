package backend.itracker.tracker.infra.oauth.kakao.dto

import backend.itracker.tracker.oauth.Member
import backend.itracker.tracker.oauth.OauthId
import backend.itracker.tracker.oauth.OauthServerType
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(SnakeCaseStrategy::class)
data class KakaoMemberResponse(
    val id: Long,
    val connectedAt: LocalDateTime?,
    val hasSignedUp: Boolean?,
    val kakaoAccount: KakaoAccount
) {

    fun toDomain(): Member {
        return Member(
            oauthId = OauthId(id.toString(), OauthServerType.KAKAO),
            nickname = kakaoAccount.profile.nickname
        )
    }

    @JsonNaming(SnakeCaseStrategy::class)
    class KakaoAccount(
        val profileNeedsAgreement: Boolean?,
        val profileNicknameNeedsAgreement: Boolean?,
        val profileImageNeedsAgreement: Boolean?,
        val profile: Profile,
        val nameNeedsAgreement: Boolean?,
        val name: String?,
        val emailNeedsAgreement: Boolean?,
        val isEmailValid: Boolean?,
        val isEmailVerified: Boolean?,
        val email: String?,
        val ageRangeNeedsAgreement: Boolean?,
        val ageRange: String?,
        val birthyearNeedsAgreement: Boolean?,
        val birthyear: String?,
        val birthdayNeedsAgreement: Boolean?,
        val birthday: String?,
        val birthdayType: String?,
        val genderNeedsAgreement: Boolean?,
        val gender: String?,
        val phoneNumberNeedsAgreement: Boolean?,
        val phoneNumber: String?,
        val ciNeedsAgreement: Boolean?,
        val ci: String?,
        val ciAuthenticatedAt: LocalDateTime?
    )

    @JsonNaming(SnakeCaseStrategy::class)
    data class Profile(
        val nickname: String,
        val thumbnailImageUrl: String?,
        val profileImageUrl: String?,
        val isDefaultImage: Boolean?
    )
}
