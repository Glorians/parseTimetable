import model.*

class Assembler {

    private val listGroups = mutableMapOf<String, Group>()


    fun createGroup(name: String, subgroup: ArrayList<Subgroup>){
        val group = Group(name, subgroup)
        listGroups[name] = group
    }

    fun createSubgroup(name: String, weeks: ArrayList<Week>): Subgroup {
        return Subgroup(name, weeks)
    }

    fun createWeek(parity: Int, listDays: ArrayList<Day>): Week {
        return Week(parity, listDays)
    }

    fun createDay(name: String, listSubject: ArrayList<Subject>): Day {
        return Day(name, listSubject)
    }

    fun createSubject(name: String): Subject {
        return Subject(name)
    }

    fun getListGroups(): MutableMap<String, Group> {
        return listGroups
    }


}