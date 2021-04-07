package presentation.base

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    return sdf.format(Date())
}