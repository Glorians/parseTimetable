package model

data class Group (var name: String, val listSubgroup: MutableList<Subgroup>) {

    fun cloneGroup(nameGroup: String): Group {
        return Group(nameGroup, listSubgroup)
    }

    fun rename(nameGroup: String) {
        this.name = nameGroup
    }

}