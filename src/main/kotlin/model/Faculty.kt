package model

data class Faculty(val name: String, val listGroups: MutableMap<String, Group>)