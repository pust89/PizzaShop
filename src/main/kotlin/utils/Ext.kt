package utils

import data.dto.PResult

inline fun <reified T : Any> T?.toSuccessResult(): PResult<T> =
    this?.let {
        successResult(it)
    } ?: emptyResult()

inline fun <reified T : Throwable> T.toErrorResult(): PResult.Error =
   errorResult(this)


inline fun <reified T : Any> Any.successResult(data: T) = PResult.Success(data)
fun Any.errorResult(exception: Throwable) = PResult.Error(exception)
fun Any?.emptyResult() = PResult.Empty