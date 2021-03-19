package presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BaseScreen {
    val coroutineJob = Job()
    val scope = CoroutineScope(coroutineJob)

    abstract fun initView(): Unit

    open fun destroy() {
        coroutineJob.cancel()
    }
}