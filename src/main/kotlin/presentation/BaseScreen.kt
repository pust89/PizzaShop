package presentation

import data.dto.PResult

abstract class BaseScreen {


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

    abstract fun displayHandledResult(result: PResult.Success<*>)

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
}