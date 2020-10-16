package utils

class CheckerDays() : Checker() {

    private val nameListDays = mutableListOf(
            "ПОНЕДІЛОК", "ВІВТОРОК", "СЕРЕДА",
            "ЧЕТВЕРГ", "ПЯТНИЦЯ", "СУБОТА")
    private val listDays = mutableListOf<StatusDay>()

    init {
        for (day in nameListDays) {
            listDays.add(StatusDay(day))
        }
    }

    fun checkDaysInWeek(str: String): String {
        if (str.length < 2) {
            for (day in listDays) {
                if (!day.status) {
                    day.checkLetters(str.toCharArray()[0])
                }
                if (day.status){
                    return day.name
                }
            }
        }
        return ""
    }
}

class StatusDay(val name: String) {
    var status = false
    private val letterMap: MutableMap<Char, Boolean> = mutableMapOf()

    init {
        val arrayChars = name.toCharArray()
        for (chars in arrayChars) {
            letterMap[chars] = false
        }
    }

    fun checkLetters(char: Char) {
        for (letter in letterMap) {
            if (char == letter.key && !letter.value) {
                letter.setValue(true)
            }
            else status = true
        }
    }

}