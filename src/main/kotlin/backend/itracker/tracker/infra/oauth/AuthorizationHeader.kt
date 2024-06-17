package backend.itracker.tracker.infra.oauth

private const val BEARER = "Bearer "

data class AuthorizationHeader(
    private val value: String
) {

    fun isNotBearerAuth() : Boolean {
        return !value.startsWith(BEARER)
    }

    fun parseHeader() : String {
        return value.substring(BEARER.length)
    }
}
