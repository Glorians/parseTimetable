package model

data class Subgroup (val name: String) {
    var listWeek = mutableListOf<Week>()

    fun addWeeksList(listWeeks: MutableList<Week>) {
        this.listWeek = listWeeks
    }
}