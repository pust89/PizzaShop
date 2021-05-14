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

    fun getAdminByIdQuery(adminId: Int): String {
        return "SELECT * FROM ${AdminsTable.TABLE_NAME} WHERE ${AdminsTable.COLUMN_ID} = '$adminId';"
    }
}

object WorkerTable {
    const val TABLE_NAME = "workers"
    const val COLUMN_ID = "id"
    const val COLUMN_FIRST_NAME = "firstName"
    const val COLUMN_SECOND_NAME = "secondName"

    fun getWorkerByIdQuery(workerId: Int): String {
        return "SELECT * FROM ${WorkerTable.TABLE_NAME} WHERE $COLUMN_ID = '$workerId';"
    }
}

object OrderTable {
    const val TABLE_NAME = "orders"
    const val COLUMN_ID = "id"
    const val COLUMN_WORKER_ID = "workerId"
    const val COLUMN_CHECK_ID = "checkId"



    fun createSaveOrderQuery(workerId: Int, checkId: Int): String {
        return "INSERT ${OrderTable.TABLE_NAME} ($COLUMN_WORKER_ID, $COLUMN_CHECK_ID)" +
                " VALUES ('$workerId', '$checkId');"
    }

    const val QUERY_SELECT_ALL = "SELECT * FROM ${OrderTable.TABLE_NAME} ORDER BY '$COLUMN_ID' DESC;"

}

object CheckTable {
    const val TABLE_NAME = "checks"
    const val COLUMN_ID = "id"
    const val COLUMN_ADMIN_ID = "adminId"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_BILL = "bill"
    const val COLUMN_DATE = "date"

    const val QUERY_GET_LAST_INSERTED_ID = "SELECT MAX($COLUMN_ID) FROM ${CheckTable.TABLE_NAME};"

    fun createSaveCheckQuery(adminId: Int, description: String, bill: Int, date: String): String {
        return "INSERT ${CheckTable.TABLE_NAME} ($COLUMN_ADMIN_ID, $COLUMN_DESCRIPTION, $COLUMN_BILL, $COLUMN_DATE)" +
                " VALUES ('$adminId','$description','$bill','$date'); "
    }

    fun getCheckByIdQuery(checkId: Int): String {
        return "SELECT * FROM ${CheckTable.TABLE_NAME} WHERE ${CheckTable.COLUMN_ID} = '$checkId';"
    }


}