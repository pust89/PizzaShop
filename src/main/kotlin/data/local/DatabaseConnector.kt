package data.local

import domain.models.Pizza
import domain.models.User
import java.sql.*
import java.util.*

object DatabaseConnector {
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
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
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
    fun signIn(login: String, password: String): User? {
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(WorkerTable.createLoginQuery(login, password))
        return if (resultSet.fetchSize == 1) {
            User(
                id = resultSet.getInt(WorkerTable.COLUMN_ID),
                firstName = resultSet.getString(WorkerTable.COLUMN_FIRST_NAME),
                secondName = resultSet.getString(WorkerTable.COLUMN_SECOND_NAME)
            )
        } else {
            null
        }
    }

    fun getPizzaMenu(): List<Pizza> {
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(PizzaTable.QUERY_SELECT_ALL)
        val resultLit = mutableListOf<Pizza>()
        while (resultSet.next()) {
            resultSet.apply {
                resultLit.add(
                    Pizza(
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
}