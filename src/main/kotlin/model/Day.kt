package model

data class Day(val nameDay: String) {
    val listSubject = mutableMapOf<Int,Subject>()

    fun addSubject(numSubject: Int,subject: Subject) {
        listSubject[numSubject] = subject
    }
}