package domain.models

data class Worker(
    val id: Int,
    val firstName: String,
    val secondName: String
) {
    companion object {
        val DEFAULT_WORKER = Worker(-1, "", "")
    }
}