package model

data class Day(val nameDay: String) {
    val listSubject = arrayListOf<Subject>()

    fun addSubject(subject: Subject) {
        listSubject.add(subject)
    }
}