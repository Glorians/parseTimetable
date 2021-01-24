import model.*
import utils.MyColor

class Reader(listFaculty: List<Faculty>) {

    init {
        for (faculty in listFaculty) {
            println(MyColor.ANSI_BLUE + "Факультет: ${faculty.name}" + MyColor.ANSI_RESET)
            readFaculty(faculty.name, faculty)
        }
    }

    private fun readFaculty(nameFaculty: String, faculty: Faculty) {
        for (group in faculty.getListGroups()) {
            println(MyColor.ANSI_GREEN + "Группа: ${group.name}" + MyColor.ANSI_RESET)
            readGroup(group.name, group)
        }
    }

    private fun readGroup(nameGroup: String, group: Group) {
        for (subgroup in group.subgroup) {
            println(MyColor.ANSI_GREEN + "Підгруппа: ${subgroup.name}" + MyColor.ANSI_RESET)
            readSubgroup(subgroup.name, subgroup)
        }
    }

    private fun readSubgroup(nameSubgroup: String, subgroup: Subgroup) {
        for (week in subgroup.listWeek) {
            print(MyColor.ANSI_CYAN)
            if (week.parity == 1) {
                println("Верхня неділя")
            }
            if (week.parity == 2) {
                println("Нижня неділя")
            }
            print(MyColor.ANSI_RESET)
            readWeek(week.parity, week)
        }
    }

    private fun readWeek(parity: Int, week: Week) {
        for (day in week.listDay) {
            println(MyColor.ANSI_YELLOW + "День: ${day.name}" + MyColor.ANSI_RESET)
            readDay(day.name, day)
        }
    }

    private fun readDay(nameDay: String, day: Day) {
        for (subject in day.listSubject) {
            println(MyColor.ANSI_PURPLE + "Пара #${subject.getPosition()}" + MyColor.ANSI_RESET)
            readSubject(subject)
        }
    }

    private fun readSubject(subject: Subject) {
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