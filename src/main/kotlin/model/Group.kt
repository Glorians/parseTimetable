package model

data class Group (var name: String, val subgroup: MutableList<Subgroup>) {

    fun cloneGroup(nameGroup: String): Group {
        return Group(nameGroup, subgroup)
    }

    fun rename(nameGroup: String) {
        this.name = nameGroup
    }

}