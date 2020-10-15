fun main(args: Array<String>) {

    val nameFile: String = "timetable.xls"
    val parseFile = ParseFile(nameFile)
    parseFile.parse()
}