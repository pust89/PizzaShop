package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import presentation.base.errorLiveData
import presentation.base.loadingLiveData
import presentation.base.titleLiveData
import presentation.login.isNeedLogout
import presentation.login.worker

/**
 * Отображает заголовок
 */
@Composable
fun customizeTitleDisplay(processSignOut: () -> Unit, processCreateNewOrder: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    ) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = titleLiveData.value,
            modifier = Modifier.align(Alignment.Center)
        )

        if (isNeedLogout.value) {
            Button(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = {
                    processSignOut()
                }) {
                Text("Выйти")
            }

            Button(
                modifier = Modifier.align(Alignment.TopEnd).padding(end = 100.dp),
                onClick = {
                    processCreateNewOrder()
                }) {
                Text("Новый зака")
            }
        }
    }
}

/**
 * Отображает загрузка в приложении
 */
@Composable
fun customizeDownloadingDisplay() {
    if (loadingLiveData.value) {
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

/**
 * Отображает ошибки в приложении
 */
@Composable
fun customizeErrorDisplay() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = TextUnit.Sp(18),
            color = Color.Red,
            text = errorLiveData.value,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

    }
}

/**
 * Метод добавляет отображение залогиненного пользователя
 */
@Composable
fun customizeWorkerDisplay() {
    if (worker.value != null) {
        Column(Modifier.fillMaxWidth().padding(top = 10.dp).wrapContentHeight().background(Color.Magenta)) {
            worker.value?.run {
                Text(
                    fontSize = TextUnit.Companion.Sp(25),
                    text = "Работник: id=$id, $firstName $secondName",
                )
            }
        }
    }
}