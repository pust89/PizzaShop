package domain.models

data class CheckUi(
    val checkId: Int,
    val adminUi: AdminUi,
    val description: String,
    val bill: Int,
    val date: String
) {

    override fun toString() = "Чек №:${checkId}\n\n "+
            "Кассир:${adminUi.secondName} ${adminUi.firstName}\n\n" +
            "$description\n\nСчет:$bill руб.\n\n$date"
}
