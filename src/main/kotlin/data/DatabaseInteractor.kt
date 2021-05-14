package data

import data.local.*
import domain.models.*
import presentation.login.currentAdmin
import java.sql.*
import java.util.*

/**
 * Класс отвечающий за взаимодействие с базой данных
 */
object DatabaseInteractor {
    private const val USERNAME = "admin"
    private const val PASSWORD = "d1d2d3d4"
    private const val MY_SQL_DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/pizza_shop"

    private val connectionProperties: Properties by lazy {
        Properties().apply {
            this["user"] = USERNAME
            this["password"] = PASSWORD
        }
    }

    private val connection: Connection by lazy {
        val x = Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
        DriverManager.getConnection(MY_SQL_DATABASE_URL, connectionProperties)
    }

    fun closeConnection() {
        if (!connection.isClosed) {
            connection.close()
        }
    }

    /**
     * Заходим в систему по логину и паролю.
     */
    fun signIn(login: String, password: String): Admin? {
        val statement: Statement = connection.createStatement()
        val loginQuery = AdminsTable.createLoginQuery(login, password)
        println(loginQuery)
        val resultSet: ResultSet = statement.executeQuery(loginQuery)
        return if (resultSet.next()) {
            Admin(
                id = resultSet.getInt(WorkerTable.COLUMN_ID),
                firstName = resultSet.getString(WorkerTable.COLUMN_FIRST_NAME),
                secondName = resultSet.getString(WorkerTable.COLUMN_SECOND_NAME)
            )
        } else {
            null
        }
    }

    /**
     * Возвращает работника по id
     */
    fun getWorkerById(workerId: Int): Worker? {
        val statement: Statement = connection.createStatement()
        val query = WorkerTable.getWorkerByIdQuery(workerId)
        println(query)
        val resultSet: ResultSet = statement.executeQuery(query)
        return if (resultSet.next()) {
            Worker(
                id = resultSet.getInt(WorkerTable.COLUMN_ID),
                firstName = resultSet.getString(WorkerTable.COLUMN_FIRST_NAME),
                secondName = resultSet.getString(WorkerTable.COLUMN_SECOND_NAME)
            )
        } else {
            null
        }
    }

    /**
     * Возвращает список всех пицц (меню)
     */
    fun getPizzaMenu(): List<PizzaDatabase> {
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(PizzaTable.QUERY_SELECT_ALL)
        val resultLit = mutableListOf<PizzaDatabase>()
        while (resultSet.next()) {
            resultSet.apply {
                resultLit.add(
                    PizzaDatabase(
                        id = getInt(PizzaTable.COLUMN_ID),
                        name = getString(PizzaTable.COLUMN_NAME),
                        imageUrl = getString(PizzaTable.COLUMN_IMAGE),
                        price = getInt(PizzaTable.COLUMN_PRICE)
                    )
                )
            }

        }
        return resultLit
    }


    /**
     * Сохраняет новый  чек и возвращает его id
     */
    fun saveNewCheck(admin: Admin, order: SaveOrderDbModel): Boolean {
        var insertedId = 0
        val statement: Statement = connection.createStatement()
        val insertCheckQuery = order.run {
            CheckTable.createSaveCheckQuery(admin.id, orderPizzas, bill, date)
        }
        statement.execute(insertCheckQuery)
        val resultSet: ResultSet = statement.executeQuery(CheckTable.QUERY_GET_LAST_INSERTED_ID)
        while (resultSet.next()) {
            resultSet.apply {
                insertedId = getInt("MAX(id)")
            }
        }
        println("insertedId = $insertedId")
        return saveNewOrder(insertedId,order )
    }

    /**
     * Сохраняет новый заказ и чек
     */
    fun saveNewOrder(checkId: Int, order: SaveOrderDbModel): Boolean {
        val statement: Statement = connection.createStatement()
        val insertOrderQuery = order.run {
            OrderTable.createSaveOrderQuery(workerId, checkId)
        }
        return statement.execute(insertOrderQuery)
    }

    /**
     * Возвращает список всех заказов
     */
    fun getAllOrders(): List<DisplayOrderItem> {
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(OrderTable.QUERY_SELECT_ALL)
        val resultLit = mutableListOf<DisplayOrderItem>()
        while (resultSet.next()) {
            resultSet.apply {
                val workerId = getInt(OrderTable.COLUMN_WORKER_ID)
                val checkId = getInt(OrderTable.COLUMN_CHECK_ID)
                getWorkerById(workerId)?.let { worker ->
                    getCheckById(checkId)?.let { checkUi ->
                        resultLit.add(
                            DisplayOrderItem(
                                orderId = getInt(OrderTable.COLUMN_ID),
                                worker = worker,
                                orderPizzas = checkUi.description,
                                bill = checkUi.bill,
                                date = checkUi.date,
                                checkUi = checkUi
                            )
                        )
                    }
                }
            }

        }
        return resultLit.sortedByDescending { it.orderId }
    }

    /**
     * Возвращает чек по id
     */
    fun getCheckById(checkId: Int): CheckUi? {
        val statement: Statement = connection.createStatement()
        val query = CheckTable.getCheckByIdQuery(checkId)
        println(query)
        val resultSet: ResultSet = statement.executeQuery(query)
        return if (resultSet.next()) {
            val adminId = resultSet.getInt(CheckTable.COLUMN_ADMIN_ID)
            val adminUi = getAdminById(adminId)!!
            CheckUi(
                adminUi = adminUi,
                checkId = resultSet.getInt(CheckTable.COLUMN_ID),
                description = resultSet.getString(CheckTable.COLUMN_DESCRIPTION),
                bill = resultSet.getInt(CheckTable.COLUMN_BILL),
                date = resultSet.getString(CheckTable.COLUMN_DATE)
            )
        } else {
            null
        }
    }

    /**
     * Возвращает работника по id
     */
    fun getAdminById(adminId: Int): AdminUi? {
        val statement: Statement = connection.createStatement()
        val query = AdminsTable.getAdminByIdQuery(adminId)
        println(query)
        val resultSet: ResultSet = statement.executeQuery(query)
        return if (resultSet.next()) {
            AdminUi(
                firstName = resultSet.getString(AdminsTable.COLUMN_FIRST_NAME),
                secondName = resultSet.getString(AdminsTable.COLUMN_SECOND_NAME)
            )
        } else {
            null
        }
    }

}