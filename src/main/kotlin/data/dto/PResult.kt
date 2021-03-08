package data.dto

/**
 * DTO class
 */
sealed class PResult<out T : Any>() {

    var isHandled = false

    fun handle() {
        isHandled = true
    }

    data class Success<out T : Any>(val data: T) : PResult<T>()

    data class Error(val exception: Throwable) : PResult<Nothing>() {
        val message = exception.message ?: "Unknown error"
    }

    object Loading : PResult<Nothing>()

    object Empty : PResult<Nothing>()
}