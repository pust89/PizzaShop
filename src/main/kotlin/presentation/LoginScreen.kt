package presentation

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import data.dto.PResult
import data.repo.Repository
import domain.models.User
import domain.repo.IRepository
import kotlinx.coroutines.launch


val appRepository: IRepository = Repository


fun main() {
    LoginScreen()
}

class LoginScreen : BaseScreen() {

    init {
        initView()
    }

    override fun initView(): Unit = Window(title = "Compose for Desktop", size = IntSize(1024, 768)) {

        // fields
        val loginLiveData: MutableState<String> = remember { mutableStateOf<String>("") }
        val passwordLiveData: MutableState<String> = remember { mutableStateOf<String>("") }
        val errorLiveData: MutableState<String> = remember { mutableStateOf<String>("") }
        val loadingLiveData: MutableState<Boolean> = remember { mutableStateOf<Boolean>(false) }

        fun showLoading() {
            loadingLiveData.value = true
            println("Show loading")
        }

        fun hideLoading() {
            loadingLiveData.value = false
            println("Hide loading")
        }

        fun showError(message: String) {
            println("Show error:${message}")
            errorLiveData.value = message
            hideLoading()
        }

        fun handledSuccessResult(result: PResult.Success<*>) {
            (result.data as? User)?.let {
                println(it)

            }
        }

        //result handler
        fun handleResult(result: PResult<*>) {
            if (!result.isHandled) {
                result.handle()
                when (result) {
                    is PResult.Error -> showError(result.message)
                    is PResult.Success -> {
                        hideLoading()
                        handledSuccessResult(result)
                    }
                    is PResult.Empty -> {
                    }
                }
            }
        }

        fun processSignIn(login: String, password: String) {
            scope.launch {
                showLoading()
                handleResult(appRepository.signIn(login, password))
            }
        }

        MaterialTheme {
            Column(Modifier.fillMaxWidth().fillMaxHeight().padding(top=25.dp), Arrangement.spacedBy(50.dp)) {
                drawHelloFiled()
                showDownloading(loadingLiveData)
                showLoginInputField(loginLiveData)
                showPasswordInputField(passwordLiveData)

                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        processSignIn(login = loginLiveData.value, password = passwordLiveData.value)
                    }) {
                    Text("SignIn")
                }

                showErrorFiled(errorLiveData)

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
        Column(Modifier.fillMaxWidth()) {
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
    private fun showErrorFiled(error: MutableState<String>) {
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(5.dp)
        ) {
            Text(
                fontSize = TextUnit.Companion.Sp(18),
                color = Color.Red,
                text = error.value,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }

    @Composable
    private fun showDownloading(isLoading: MutableState<Boolean>) {
        if (isLoading.value) {
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }


    private fun processSignIn(login: String, password: String) {
        scope.launch {
            val result = appRepository.signIn(login, password)
        }
    }
}