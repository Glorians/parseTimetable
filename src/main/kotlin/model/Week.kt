package model

data class Week(val parity: Int) {
    var listDay = mutableListOf<Day>()

    fun addDays(listDays: MutableList<Day>) {
        this.listDay = listDays
    }

}