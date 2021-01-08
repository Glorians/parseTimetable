import model.*
import utils.MyColor
import utils.getListNamesDays
import utils.getListNamesSubgroups

class Assembler {

    var currentNameDay = ""
    var currentSubgroup = ""
    var currentPositionSubject = 0
    var currentWeek = 0
    private var statusDefault = false
    private var statusSubgroups = false
    private var statusParityWeek = false
    private var statusParityAndSubgroup = false

    lateinit var group: Group
    private var nameGroup = ""
    private val listSubgroupName = getListNamesSubgroups()
    private val sizeDay = 8

    fun createGroup(name: String){
        group = Group(name, generateSubgroup())
    }

    fun createSubject(listValue: ArrayList<Any>){
        var nameSubject: Any? = null
        var teacher: Any? = null
        var room: Any? = null
        if (listValue.isNotEmpty()) {

            when (listValue.size) {
                1 -> {
                    nameSubject = listValue[0]
                }
                2 -> {
                    nameSubject = listValue[0]
                    teacher = listValue[1]
                }
                3 -> {
                    nameSubject = listValue[0]
                    teacher = listValue[1]
                    room = listValue[2]
                    if (room is Double) {
                        room = room.toInt()
                    }
                }
            }

            if (nameSubject is String) {
                val subject = Subject(nameSubject)
                if (teacher is String) {
                    subject.setTeacher(teacher)
                }
                if (room is Int) {
                    subject.setClassroom(room)
                }

                subject.setPosition(currentPositionSubject)
                addSubjectInDay(subject)
            }
        }
    }

    private fun addSubjectInDay(subject: Subject) {
        when {
            statusDefault -> {
                for (nameSubgroup in listSubgroupName) {
                    for (parity in 1..2) {
                        val day = getDay(nameSubgroup, parity)
                        day.addSubject(currentPositionSubject, subject)
                    }
                }
            }
            statusSubgroups -> {
                for (parity in 1..2) {
                    val day = getDay(currentSubgroup, parity)
                    day.addSubject(currentPositionSubject, subject)
                }
            }
            statusParityWeek -> {
                for (nameSubgroup in listSubgroupName) {
                    val day = getDay(nameSubgroup, currentWeek)
                    day.addSubject(currentPositionSubject, subject)
                }
            }
            statusParityAndSubgroup -> {
                val day = getDay(currentSubgroup, currentWeek)
                day.addSubject(currentPositionSubject, subject)
            }
        }
    }

    fun printingGroup() {
        println(MyColor.ANSI_GREEN+"Группа ${group.nameGroup}"+MyColor.ANSI_RESET)
        for (nameSubgroup in listSubgroupName) {
            val subgroup = group.subgroups[nameSubgroup]
            for (parity in 1..2) {
                print(MyColor.ANSI_PURPLE)
                println("Підгруппа: ${subgroup!!.nameSubgroup}")
                println("Тиждень: $parity")
                print(MyColor.ANSI_RESET)
                for (nameDay in getListNamesDays()) {
                    println(MyColor.ANSI_YELLOW+ nameDay +MyColor.ANSI_RESET)
                    val day = subgroup.listWeeks[parity]!!.listDays
                    for(subjectNum in 1..sizeDay) {
                        val subject = day[nameDay]!!.listSubject[subjectNum]
                        if (subject?.name != null) {
                            println("Пара #${subject.getPosition()} " +
                                    "Назва: ${subject.name} " +
                                    "Вчитель: ${subject.getTeacher()} " +
                                    "Кабінет: ${subject.getClassroom()}")
                        }

                    }

                }

            }
        }
    }

    private fun generateSubgroup(): MutableMap<String, Subgroup> {
        val subgroupA = Subgroup("А")
        val subgroupB = Subgroup("Б")
        subgroupA.addWeeksList(generateWeeks())
        subgroupB.addWeeksList(generateWeeks())

        return mutableMapOf(
            "А" to subgroupA,
            "Б" to subgroupB
        )
    }

    private fun generateWeeks(): MutableMap<Int, Week> {
        val week1 = Week(1)
        val week2 = Week(2)
        week1.addDays(generateDays())
        week2.addDays(generateDays())
        return mutableMapOf(1 to week1, 2 to week2)
    }

    private fun generateDays(): MutableMap<String,Day> {
        val listNamesDays = getListNamesDays()
        val listDays = mutableMapOf<String, Day>()
        for (nameDay in listNamesDays) {
            listDays[nameDay] = Day(nameDay)
        }
        return listDays
    }

    private fun getDay(nameSubgroup: String, parityWeek: Int): Day {
        return group.subgroups[nameSubgroup]!!.listWeeks[parityWeek]!!.listDays[currentNameDay]!!
    }

    fun clearStatus() {
        statusDefault = false
        statusParityWeek = false
        statusSubgroups = false
        statusParityAndSubgroup = false
    }

    fun statusDefault() {
        statusDefault = true
    }

    fun statusParityWeek() {
        statusParityWeek = true
    }

    fun statusSubgroups() {
        statusSubgroups = true
    }

    fun statusParityAndSubgroup() {
        statusParityAndSubgroup = true
    }

}