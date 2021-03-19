package data.local

object PizzaTable {
    const val TABLE_NAME = "menu_pizza"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_PRICE = "price"

    const val QUERY_SELECT_ALL = "SELECT * FROM menu_pizza;"
}

object WorkerTable {
    const val TABLE_NAME = "workers"
    const val COLUMN_ID = "id"
    const val COLUMN_LOGIN = "login"
    const val COLUMN_PASSWORD = "password"
    const val COLUMN_FIRST_NAME = "firstName"
    const val COLUMN_SECOND_NAME = "secondName"

    fun createLoginQuery(login: String, password: String): String {
        return "SELECT * FROM workers WHERE login = $login AND password=$password;"
    }

}