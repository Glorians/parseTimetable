import com.google.gson.Gson
import com.google.gson.GsonBuilder
import model.Faculty
import utils.MyColor
import java.io.File

class JsonHelper {

    fun toJsonFaculty(mapFaculty: Map<String, Faculty>) {

        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

//        val jsonFaculty: String = gson.toJson(mapFaculty)
//        println(jsonFaculty)

        val jsonFacultyPretty = gsonPretty.toJson(mapFaculty)
        createFileJson(jsonFacultyPretty)
//        println(jsonFacultyPretty)
    }

    private fun createFileJson(strJson: String) {
        val fileName = "timetable.json"

        try {
            val file = File(fileName)
            file.writeText(strJson)
            printInfoCreatingFile(true)
        }
        catch (e: Exception) {
            printInfoCreatingFile(false)
        }


    }

    private fun printInfoCreatingFile(status: Boolean) {
        val textSuccessful = "Файл успішно створено"
        val textDenied = "Помилка! Файл не вдалося створити"
        if (status) {
            print(MyColor.ANSI_GREEN)
            printBorder("\n"+ textSuccessful)
            print(MyColor.ANSI_RESET)
        }
        else {
            print(MyColor.ANSI_RED)
            printBorder("\n"+ textDenied)
            println(MyColor.ANSI_RESET)
        }

    }

    private fun printBorder(text: String) {
        for (i in 0..text.length) {
            print("_")
        }
        println(text)
        for (i in 0..text.length) {
            print("_")
        }
    }
}