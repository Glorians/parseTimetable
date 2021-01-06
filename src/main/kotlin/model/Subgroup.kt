package model

data class Subgroup (val nameSubgroup: String) {
    private val listWeeks = arrayListOf<Week>()

    fun addWeek(week: Week) {
        listWeeks.add(week)
    }
}