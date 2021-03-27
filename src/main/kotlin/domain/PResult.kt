package domain

/**
 * DTO class
 */
sealed class PResult<out T : Any?>() {

    var isHandled = false

    fun handle() {
        isHandled = true
    }

    data class Success<out T : Any>(val data: T) : PResult<T>()

    data class Error(val exception: Throwable) : PResult<Nothing>() {
        val message = exception.message ?: "Unknown error"
    }

    object Empty : PResult<Nothing>()
}

inline fun <reified T : Any> T?.toSuccessResult(): PResult<T> =
    this?.let {
        successResult(it)
    } ?: emptyResult()

inline fun <reified T : Throwable> T.toErrorResult(): PResult.Error =
    errorResult(this)


inline fun <reified T : Any> Any.successResult(data: T) = PResult.Success(data)
fun Any.errorResult(exception: Throwable) = PResult.Error(exception)
fun Any?.emptyResult() = PResult.Empty