package backend.itracker.tracker.oauth

enum class OauthServerType {

    KAKAO;

    companion object {
        fun from(value: String): OauthServerType {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true)}
                ?: throw IllegalStateException("지원하지 않는 OauthServerType 입니다. value=$value")
        }
    }
}
