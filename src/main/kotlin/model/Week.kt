package model

data class Week(val parity: Int) {
    private val listDays = arrayListOf<Day>()

    fun addDay(day: Day) {
        listDays.add(day)
    }

}