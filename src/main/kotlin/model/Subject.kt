package model

class Subject (val name: String) {
    private var position: Int = 0
    private var classroom: Int = 0
    private lateinit var teacher: String

    fun setPosition(position: Int) {
        this.position = position
    }

    fun setClassroom(classroom: Int) {
        this.classroom = classroom
    }

    fun setTeacher(teacher: String) {
        this.teacher = teacher
    }
}