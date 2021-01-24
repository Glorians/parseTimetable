import model.*
import utils.Checker
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

    private val checker = Checker()

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

            if (nameSubject is String && !checker.checkVoidSubjectName(nameSubject)) {
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
                        day.addSubject(subject)
                    }
                }
            }
            statusSubgroups -> {
                for (parity in 1..2) {
                    val day = getDay(currentSubgroup, parity)
                    day.addSubject(subject)
                }
            }
            statusParityWeek -> {
                for (nameSubgroup in listSubgroupName) {
                    val day = getDay(nameSubgroup, currentWeek)
                    day.addSubject(subject)
                }
            }
            statusParityAndSubgroup -> {
                val day = getDay(currentSubgroup, currentWeek)
                day.addSubject(subject)
            }
        }
    }

    private fun generateSubgroup(): MutableList<Subgroup> {
        val subgroupA = Subgroup("А")
        val subgroupB = Subgroup("Б")
        subgroupA.addWeeksList(generateWeeks())
        subgroupB.addWeeksList(generateWeeks())
        return mutableListOf(subgroupA, subgroupB)
    }

    private fun generateWeeks(): MutableList<Week> {
        val week1 = Week(1)
        val week2 = Week(2)
        week1.addDays(generateDays())
        week2.addDays(generateDays())
        return mutableListOf(week1, week2)
    }

    private fun generateDays(): MutableList<Day> {
        val listNamesDays = getListNamesDays()
        val listDays = mutableListOf<Day>()
        for (nameDay in listNamesDays) {
            listDays.add(Day(nameDay))
        }
        return listDays
    }

    private fun getDay(nameSubgroup: String, parityWeek: Int): Day {
        for (subgroup in group.subgroup) {
            if (subgroup.name == nameSubgroup) {
                for (week in subgroup.listWeek) {
                    if (week.parity == parityWeek) {
                        for (day in week.listDay) {
                            if (day.name == currentNameDay) {
                                return day
                            }
                        }
                    }
                }
            }
        }
        return Day("")
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