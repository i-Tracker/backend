package backend.itracker.tracker.oauth

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class OauthId(

    @Column(name = "oauth_server_id", nullable = false)
    val oauthServerId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_server_type", nullable = false)
    val oauthServerType: OauthServerType
) {

    override fun toString(): String {
        return "OauthId(oauthServerId='$oauthServerId', oauthServerType=$oauthServerType)"
    }
}
