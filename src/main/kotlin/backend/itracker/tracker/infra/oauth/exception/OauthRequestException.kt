package backend.itracker.tracker.infra.oauth.exception

data class OauthRequestException(
    override val message: String
) : RuntimeException(message)

