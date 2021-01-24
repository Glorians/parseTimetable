package model

data class Day(val name: String) {
    val listSubject = mutableListOf<Subject>()

    fun addSubject(subject: Subject) {
        listSubject.add(subject)
    }
}