import model.Group
import model.Subgroup
import model.Subject


class AssemblerGroups {

    private var listGroups = mutableListOf<Group>()

    fun createGroup(name: String) {
        val listSubgroup = ArrayList<Subgroup>()

        val group = Group(name, listSubgroup)
    }

    fun createSubgroup(name: Char) {

    }

    fun createWeek(primary: Int) {

    }

    fun createDay(nameDay: String) {

    }

    fun createSubject(nameSubject: String) {
        val subject = Subject(nameSubject)
    }





}