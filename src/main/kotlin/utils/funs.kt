package utils

fun getListNamesDays(): ArrayList<String> {
    return arrayListOf(
        "Понеділок",
        "Вівторок",
        "Середа",
        "Четверг",
        "Пятниця",
        "Субота",
    )
}

fun getMapNamesDays(): Map<Int, String> {
    val listNamesDays = getListNamesDays()
    val result = mutableMapOf<Int, String>()
    var count = 1
    for (name in listNamesDays) {
        result[count] = name
        count++
    }
    return result
}

fun getListNamesSubgroups(): ArrayList<String> {
    return arrayListOf("А", "Б")
}