package backend.itracker.tracker.coupang.response

data class CoupangDeepLinkResponse(
    val rCode: Int,
    val rMessage: String = "",
    val data: List<Deeplink> = mutableListOf()
)

data class Deeplink(
    val originalUrl: String = "",
    val shortenUrl: String = "",
    val landingUrl: String = ""
)

