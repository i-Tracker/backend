package backend.itracker.tracker.service.vo

private const val MINIMUM_SIZE = 1
private const val MAXIMUM_SIZE = 10

data class Limit(
    val value: Int
) {

    init {
        require(value in MINIMUM_SIZE..MAXIMUM_SIZE) {
            throw IllegalArgumentException("limit는 $MINIMUM_SIZE 이상 $MAXIMUM_SIZE 이하 여야합니다. limit: $value")
        }
    }
}
