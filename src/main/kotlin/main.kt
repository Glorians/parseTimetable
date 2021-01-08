fun main(args: Array<String>) {

    val nameFile: String = "timetable.xls"
    val parseFile = ParseFile(nameFile)
    val mapFaculty = parseFile.parse()
    Reader(mapFaculty)
}