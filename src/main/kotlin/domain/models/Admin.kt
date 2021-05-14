package domain.models

/**
 * Data model
 */
data class Admin(
    val id: Int,
    val firstName: String,
    val secondName: String
)

/**
 * Ui model
 */
data class AdminUi(
    val firstName: String,
    val secondName: String
)

/**
 * Data and Ui model
 */
data class Worker(
    val id: Int,
    val firstName: String,
    val secondName: String
) {
}