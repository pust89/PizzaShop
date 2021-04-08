package data.local

/**
 * Файл содержит описания всех таблиц в базе данных
 */
object PizzaTable {
    const val TABLE_NAME = "menu_pizza"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_PRICE = "price"

    const val QUERY_SELECT_ALL = "SELECT * FROM ${PizzaTable.TABLE_NAME};"
}
object AdminsTable {
    const val TABLE_NAME = "admins"
    const val COLUMN_ID = "id"
    const val COLUMN_LOGIN = "login"
    const val COLUMN_PASSWORD = "password"
    const val COLUMN_FIRST_NAME = "firstName"
    const val COLUMN_SECOND_NAME = "secondName"

    fun createLoginQuery(login: String, password: String): String {
        return "SELECT * FROM ${AdminsTable.TABLE_NAME} WHERE $COLUMN_LOGIN = '$login' AND $COLUMN_PASSWORD = '$password';"
    }
}
object WorkerTable {
    const val TABLE_NAME = "workers"
    const val COLUMN_ID = "id"
    const val COLUMN_FIRST_NAME = "firstName"
    const val COLUMN_SECOND_NAME = "secondName"

    fun createWorkerByIdQuery(workerId: Int): String {
        return "SELECT * FROM ${WorkerTable.TABLE_NAME} WHERE $COLUMN_ID = '$workerId';"
    }
}

object OrderTable {
    const val TABLE_NAME = "orders"
    const val COLUMN_ID = "id"
    const val COLUMN_WORKER_ID = "workerId"
    const val COLUMN_ORDERED_PIZZAS = "orderPizzas"
    const val COLUMN_BILL = "bill"
    const val COLUMN_DATE = "date"


    fun createSaveOrderQuery(workerId: Int, orderPizzas: String, bill: Int, date: String): String {
        return "INSERT ${OrderTable.TABLE_NAME} ($COLUMN_WORKER_ID, $COLUMN_ORDERED_PIZZAS, $COLUMN_BILL, $COLUMN_DATE)" +
                " VALUES ('$workerId','$orderPizzas','$bill','$date');"
    }

    const val QUERY_SELECT_ALL = "SELECT * FROM ${OrderTable.TABLE_NAME} ORDER BY '$COLUMN_ID' DESC;"

}