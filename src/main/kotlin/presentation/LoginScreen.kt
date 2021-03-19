package presentation

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import data.dto.PResult
import data.repo.Repository
import domain.models.Pizza
import domain.repo.IRepository
import kotlinx.coroutines.launch
import javax.swing.text.StyleConstants


val appRepository: IRepository = Repository


fun main() {
    val loginScreen = LoginScreen()
}

class LoginScreen : BaseScreen() {

    init {
        initView()
    }

    override fun initView(): Unit = Window(title = "Compose for Desktop", size = IntSize(1024, 768)) {

        val login: MutableState<String> = remember { mutableStateOf<String>("") }
        val password: MutableState<String> = remember { mutableStateOf<String>("") }

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(50.dp)) {
                drawHelloFiled()
                showLoginInputField(login)
                showPasswordInputField(password)

                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        processSignIn(login = login.value, password = password.value)
                    }) {
                    Text("SignIn")
                }

                showErrorFiled("")

            }
        }
    }

    @Composable
    private fun drawHelloFiled() {
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(5.dp)
        ) {
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = "Welcome to Pizza App of our restaurant.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

        }
    }

    @Composable
    fun showLoginInputField(login: MutableState<String>) {
        Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
            TextField(
                value = login.value,
                onValueChange = {
                    login.value = it
                },
                label = { Text("Login") },
                visualTransformation = VisualTransformation.Companion.None,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }

    @Composable
    fun showPasswordInputField(password: MutableState<String>) {
        Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }


    @Composable
    override fun showError(message: String) {
        super.showError(message)
        showErrorFiled(message)
    }

    @Composable
    private fun showErrorFiled(error: String) {
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(5.dp)
        ) {
            Text(
                fontSize = TextUnit.Companion.Sp(18),
                color = Color.Red,
                text = error,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun displayHandledResult(result: PResult.Success<*>) {
        (result.data as? List<Pizza>)?.let { list ->
            list.forEach { pizza ->
                println(pizza)
            }
        }
    }

    private fun processSignIn(login: String, password: String) {
        scope.launch {
            showLoading()
            val result = appRepository.signIn(login, password)
            println(result)
//            handleResult(result)
        }
    }
}