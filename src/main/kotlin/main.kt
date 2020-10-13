fun main(args: Array<String>) {

    val nameFile: String = "mytimetable.xls"
    val parseFile = ParseFile(nameFile)
    parseFile.parse()
    val writeFile = WriteExelFile()
//    writeFile.testing()
}