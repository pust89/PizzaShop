package data

import data.local.OrderTable
import data.local.PizzaTable
import data.local.WorkerTable
import domain.models.OrderDatabase
import domain.models.PizzaDatabase
import domain.models.Worker
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


    fun executeMySQLQuery(connection: Connection) {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = connection.createStatement()
            resultset = stmt!!.executeQuery("SHOW DATABASES;")

            if (stmt.execute("SHOW DATABASES;")) {
                resultset = stmt.resultSet
            }

            while (resultset!!.next()) {
                println(resultset.getString("Database"))
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
    }


    private fun selectPizzaShopDatabase(connection: Connection) {
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery("SHOW DATABASES;")
        while (resultSet.next()) {
            println(resultSet.getString("Database"))
        }
    }

    /**
     * Заходим в систему по логину и паролю.
     */
    fun signIn(login: String, password: String): Worker? {
        val statement: Statement = connection.createStatement()
        val loginQuery = WorkerTable.createLoginQuery(login, password)
        println(loginQuery)
        val resultSet: ResultSet = statement.executeQuery(loginQuery)
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
     * Сохраняет новый заказ
     */
    fun saveNewOrderMenu(order: OrderDatabase): Boolean {
        val statement: Statement = connection.createStatement()
        val insertOrderQuery = order.run {
            OrderTable.createSaveOrderQuery(workerId, orderPizzas, bill, date)
        }
        return statement.execute(insertOrderQuery)
    }
}