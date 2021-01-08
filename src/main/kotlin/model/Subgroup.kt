package model

data class Subgroup (val nameSubgroup: String) {
    var listWeeks = mutableMapOf<Int,Week>()

    fun addWeeksList(listWeeks: MutableMap<Int, Week>) {
        this.listWeeks = listWeeks
    }
}