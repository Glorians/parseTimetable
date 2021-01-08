package model

data class Group (var nameGroup: String, val subgroups: MutableMap<String ,Subgroup>) {

    fun cloneGroup(nameGroup: String): Group {
        return Group(nameGroup, subgroups)
    }

    fun rename(nameGroup: String) {
        this.nameGroup = nameGroup
    }


}