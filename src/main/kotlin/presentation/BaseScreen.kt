package presentation

import androidx.compose.runtime.Composable
import data.dto.PResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BaseScreen {
    val coroutineJob = Job()
    val scope = CoroutineScope(coroutineJob)

    @Composable
    fun handleResult(result: PResult<*>) {
        if (!result.isHandled) {
            result.handle()
            when (result) {
                is PResult.Loading -> showLoading()
                is PResult.Error -> showError(result.message)
                is PResult.Success -> {
                    hideLoading()
                    displayHandledResult(result)
                }
                is PResult.Empty -> {
                    hideLoading()
                    showEmptyResult()
                }
            }
        }
    }

    abstract fun initView(): Unit

    abstract fun displayHandledResult(result: PResult.Success<*>)

    @Composable
    open fun showError(message: String) {
        hideLoading()
        println("Show error:${message}")
    }

    open fun showLoading() {
        println("Show loading")
    }

    open fun showEmptyResult() {
        println("Show empty Result")
    }

    open fun hideLoading() {
        println("Hide loading")
    }

    open fun destroy() {
        coroutineJob.cancel()
    }
}