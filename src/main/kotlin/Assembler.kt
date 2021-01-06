import model.*

class Assembler {

    private val listGroups = mutableMapOf<String, Group>()


    fun createGroup(name: String){
        val listSubgroup = arrayListOf<Subgroup>(
            createSubgroup("A"),
            createSubgroup("B")
        )

        listSubgroup[0].addWeek(createWeek(1))
        listSubgroup[0].addWeek(createWeek(2))

        listSubgroup[1].addWeek(createWeek(1))
        listSubgroup[1].addWeek(createWeek(2))


        val group = Group(name, listSubgroup)
        listGroups[name] = group
    }

    private fun createSubgroup(name: String): Subgroup {
        return Subgroup(name)
    }

    private fun createWeek(parity: Int): Week {
        return Week(parity)
    }

    fun createDay(name: String): Day {
        return Day(name)
    }

    fun createSubject(name: String): Subject {
        return Subject(name)
    }

    fun getGroup(name: String): Group? {
        return listGroups[name]
    }

    fun getListGroups(): MutableMap<String, Group> {
        return listGroups
    }


}