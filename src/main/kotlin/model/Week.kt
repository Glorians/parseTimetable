package model

data class Week(val parity: Int) {
    var listDays = mutableMapOf<String, Day>()

    fun addDays(listDays: MutableMap<String, Day>) {
        this.listDays = listDays
    }

}