package presentation

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.dto.PResult
import data.repo.Repository
import domain.models.Pizza
import domain.repo.IRepository


val MENU: IRepository = Repository


fun main() {
    val loginScreen = LoginScreen()
    loginScreen.apply {
        showLoading()
        val result = MENU.getPizzaMenu()
        loginScreen.handleResult(result)
    }

}


class LoginScreen : BaseScreen() {
    init {
        initView()
    }

    fun initView() = Window(title = "Compose for Desktop", size = IntSize(780, 640)) {
        val count = remember { mutableStateOf(0) }
        MaterialTheme {
            Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value++
                    }) {
                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
                }
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value = 0
                    }) {
                    Text("Reset")
                }

            }
        }
    }

    override fun displayHandledResult(result: PResult.Success<*>) {
        (result.data as? List<Pizza>)?.let { list ->
            list.forEach { pizza ->
                println(pizza)
            }
        }
    }

}