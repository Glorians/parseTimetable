import model.*
import utils.MyColor

class Reader(mapFaculty: Map<String, Faculty>) {

    init {
        for ((nameFaculty, faculty) in mapFaculty) {
            println(MyColor.ANSI_BLUE + "Факультет: $nameFaculty" + MyColor.ANSI_RESET)
            readFaculty(nameFaculty, faculty)
        }
    }

    private fun readFaculty(nameFaculty: String, faculty: Faculty) {
        val mapGroups = faculty.getMapGroups()
        for ((nameGroup, group) in mapGroups) {
            println(MyColor.ANSI_GREEN + "Группа: $nameGroup" + MyColor.ANSI_RESET)
            readGroup(nameGroup, group)
        }
    }

    private fun readGroup(nameGroup: String, group: Group) {
        val mapSubgroups = group.subgroups
        for ((nameSubgroup, subgroup) in mapSubgroups) {
            println(MyColor.ANSI_GREEN + "Підгруппа: $nameSubgroup" + MyColor.ANSI_RESET)
            readSubgroup(nameSubgroup, subgroup)
        }
    }

    private fun readSubgroup(nameSubgroup: String, subgroup: Subgroup) {
        val mapWeeks = subgroup.listWeeks
        for ((parity, week) in mapWeeks) {
            print(MyColor.ANSI_CYAN)
            if (parity == 1) {
                println("Верхня неділя")
            }
            if (parity == 2) {
                println("Нижня неділя")
            }
            print(MyColor.ANSI_RESET)
            readWeek(parity, week)
        }
    }

    private fun readWeek(parity: Int, week: Week) {
        val mapDays = week.listDays
        for ((nameDay, day) in mapDays) {
            println(MyColor.ANSI_YELLOW + "День: $nameDay" + MyColor.ANSI_RESET)
            readDay(nameDay, day)
        }
    }

    private fun readDay(nameDay: String, day: Day) {
        val mapSubject = day.listSubject
        for ((numSubject, subject) in mapSubject) {
            println(MyColor.ANSI_PURPLE + "Пара #$numSubject" + MyColor.ANSI_RESET)
            readSubject(numSubject, subject)
        }
    }

    private fun readSubject(numSubject: Int, subject: Subject) {
        val name = subject.name
        val teacher = subject.getTeacher()
        val classroom = subject.getClassroom()
        println(name)
        if (teacher != null) {
            println("Викладач: $teacher")
        }
        if (classroom != null) {
            println("Кабінет: $classroom")
        }


    }
}