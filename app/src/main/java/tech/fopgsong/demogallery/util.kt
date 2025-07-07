package tech.fopgsong.demogallery

fun mapValue(value: Int, fromStart: Int, fromEnd: Int, toStart: Int, toEnd: Int): Int {
    val ratio = (value - fromStart) / (fromEnd - fromStart)
    return toStart + ratio * (toEnd - toStart)
}
