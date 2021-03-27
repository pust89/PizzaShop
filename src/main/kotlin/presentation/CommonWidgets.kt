package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Отображает заголовок
 */
@Composable
fun showTitleFiled(title: MutableState<String>) {
    Column(
        Modifier.fillMaxWidth(),
        Arrangement.spacedBy(5.dp)
    ) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = title.value,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

/**
 * Отображает загрузка в приложении
 */
@Composable
fun showDownloading(isLoading: MutableState<Boolean>) {
    if (isLoading.value) {
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
fun showErrorFiled(error: MutableState<String>) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = TextUnit.Sp(18),
            color = Color.Red,
            text = error.value,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}