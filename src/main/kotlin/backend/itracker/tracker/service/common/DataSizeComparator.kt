package backend.itracker.tracker.service.common

private const val DATA_SIZE_UNIT = 2
private const val TB_TO_GB = 1024

class DataSizeComparator : Comparator<String> {

    override fun compare(o1: String, o2: String): Int {
        return changeToGigaByte(o1).compareTo(changeToGigaByte(o2))
    }

    private fun changeToGigaByte(size: String): Long {
        return when (size.takeLast(DATA_SIZE_UNIT)) {
            "GB" -> size.take(size.length - DATA_SIZE_UNIT).toLong()
            "TB" -> size.take(size.length - DATA_SIZE_UNIT).toLong() * TB_TO_GB
            else -> throw IllegalArgumentException("지원하지 않는 데이터 크기 단위 입니다. size: $size")
        }
    }
}
