package presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import domain.models.Worker

val currentWorker = mutableStateOf<Worker?>(null)

val loginLiveData = mutableStateOf<String>("login1")
val passwordLiveData = mutableStateOf<String>("password1")
val isInSystem = mutableStateOf<Boolean>(false)
val isNeedLogout = mutableStateOf<Boolean>(false)
/**
 * Агрегирует методы по созданию экрана "Вход в приложение"
 */
@Composable
fun loginScreen(
    processSignIn: () -> Unit
) {
    if (!isInSystem.value) {
        Column(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 25.dp), Arrangement.spacedBy(50.dp)) {
            isNeedLogout.value = false

            showLoginInputField(loginLiveData)
            showPasswordInputField(passwordLiveData)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    processSignIn()
                }) {
                Text("Войти")
            }
        }
    }
}

/**
 * Поле ввода логина
 */
@Composable
fun showLoginInputField(login: MutableState<String>) {
    Column(Modifier.fillMaxWidth()) {
        TextField(
            value = login.value,
            onValueChange = {
                login.value = it
            },
            label = { Text("Login") },
            visualTransformation = VisualTransformation.None,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

/**
 * Поле ввода пароля
 */
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