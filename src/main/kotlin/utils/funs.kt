package utils

import model.Group

fun getListNamesDays(): ArrayList<String> {
    return arrayListOf(
        "понеділок",
        "вівторок",
        "середа",
        "четверг",
        "пятниця",
        "субота",
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
    return arrayListOf("A", "B")
}

fun fixDoubleGroup(group: Group): ArrayList<Group> {
    val nameGroup = group.name
    val checker = Checker()
    val listGroup = arrayListOf<Group>()

    val listNameGroup = nameGroup.split(" ")
    group.rename(listNameGroup[0])
    val group2 = group.cloneGroup(listNameGroup[1])
    listGroup.add(group)
    listGroup.add(group2)
    return listGroup
}