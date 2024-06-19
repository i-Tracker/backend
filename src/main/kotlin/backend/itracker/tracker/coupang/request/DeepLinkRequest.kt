package backend.itracker.tracker.coupang.request

data class DeepLinkRequest(
    val coupangUrls: List<String>,
    val subId: String = "itracker"
)
