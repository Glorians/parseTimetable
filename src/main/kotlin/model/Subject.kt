package model

class Subject (val name: String) {
    private var position: Int = 0
    private var classroom: Int? = null
    private var teacher: String? = null

    fun setPosition(position: Int) {
        this.position = position
    }

    fun setClassroom(classroom: Int) {
        this.classroom = classroom
    }

    fun setTeacher(teacher: String) {
        this.teacher = teacher
    }

    fun getPosition(): Int {
        return position
    }

    fun getClassroom(): Int? {
        return classroom
    }

    fun getTeacher(): String? {
        return teacher
    }
}