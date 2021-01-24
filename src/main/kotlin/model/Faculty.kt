package model

data class Faculty(val name: String) {
    private lateinit var listGroup: MutableList<Group>

    fun addMapGroups(listGroups: MutableList<Group>) {
        this.listGroup = listGroups
    }

    fun getListGroups(): List<Group> {
        return listGroup
    }
}