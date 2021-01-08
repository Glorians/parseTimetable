package model

data class Faculty(val name: String) {
    private lateinit var mapGroups: MutableMap<String, Group>

    fun addMapGroups(mapGroups: MutableMap<String, Group>) {
        this.mapGroups = mapGroups
    }

    fun getMapGroups(): Map<String, Group> {
        return mapGroups
    }
}