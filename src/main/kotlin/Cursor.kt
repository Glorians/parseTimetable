import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import utils.Checker
import utils.MyColor

class Cursor(private val sheet: Sheet, private val assembler: Assembler) {

    private val countDay = 5
    private var nameGroup: String? = null
    private var startPositionGroup = 0 // Horizontal
    private var endPositionGroup = 0 // Horizontal
    private var startPositionSubject = 2 // Vertical
    private var endPositionSubject = 7 // Vertical

    private val positionNameGroup = 0 // Horizontal
    private val endTableVertical = 242 // Vertical 5 DAY
    private val sizeGroup = 3 // Horizontal
    private val sizeDay = 8 // Vertical
    private val sizeSubject = 5 // Vertical

    private val checker = Checker()

    // Determines the type of subject
    private fun scanSubject(): Analyzer {
        val arrayCells = ArrayList<Cell>() // Data subject CELLS
        var startVerticalSubject = startPositionSubject // Vertical
        val endVerticalSubject = endPositionSubject // Vertical
        val startHorizontalSubject = startPositionGroup // Horizontal
        val endHorizontalSubject = endPositionGroup // Horizontal

        //Run along the line subject VERTICAL
        for (lineSubject in startVerticalSubject..endVerticalSubject) {
            //Run along the column subject Horizontal
            for (width in startHorizontalSubject..endHorizontalSubject) {
                // Added cells in array
                val cell = sheet.getRow(lineSubject).getCell(width)
                arrayCells.add(cell)
            }
            startVerticalSubject++

        }
        return Analyzer(arrayCells)
    }

    private fun selectSubject(numSubject: Int) {

        val analyzer = scanSubject() // the object stores information about the subject

        // If the most common subject, without subgroups and prime weeks
        fun defaultWork() {
            //Run along the line subject VERTICAL
            for (lineSubject in startPositionSubject..endPositionSubject) {
                //Run along the column subject Horizontal
                for (width in startPositionGroup..endPositionGroup) {
                    // Printing values
                    val value = getValueCell(lineSubject, width)
                    if (value != null) {
                        println(value)
                    }
                }
                startPositionSubject++
            }
            endPositionSubject = startPositionSubject + sizeSubject // End position of the next subject
        }

        // If the subject has subgroups
        fun subgroupWork() {
            var startHorizontalSubject = startPositionGroup // Horizontal
            var endHorizontalSubject = endPositionGroup - 2 // Horizontal
            var countLineSubject = 0 // Counter
            val sizeSubgroup = 2

            // Go through two subgroups
            for (column in 1..2) {

                // Printing
                if (column == 2) {
                    println("|")
                }
                // Run along the line subject VERTICAL
                for (lineSubject in startPositionSubject..endPositionSubject) {
                    //Run along the column subject Horizontal
                    for (width in startHorizontalSubject..endHorizontalSubject) {
                        val value = getValueCell(lineSubject, width)
                        if (value != null) {
                            println(value)
                        }
                    }
                    countLineSubject++
                }
                // Transition to the next subgroup
                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }
            // Transition to the next subject
            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        // If subject with parity weeks
        fun defaultWorkWithParityWeek() {
            var startVerticalSubject = startPositionSubject // Vertical
            var endVerticalSubject = endPositionSubject-3 // Vertical

            // We go along parity weeks
            for (parity in 1..2) {
                // Run along the line subject VERTICAL
                for (lineSubject in startVerticalSubject..endVerticalSubject) {
                    //Run along the column subject Horizontal
                    for (width in startPositionGroup..endPositionGroup) {
                        // Printing values
                        val value = getValueCell(lineSubject, width)
                        if (value != null) {
                            print("$value ")
                        }
                    }
                    startPositionSubject++
                }
                // Printing border
                if (parity == 1) {
                    println("\n_____________")
                }
                // Next week
                startVerticalSubject = endVerticalSubject + 1
                endVerticalSubject += 2
            }
            println("\n")
            endPositionSubject = startPositionSubject + sizeSubject // End position next subject
        }

        // If the subject has both subgroups and parity weeks
        fun subgroupWorkWithParityWeek() {

            // Data for subgroup
            var startHorizontalSubject = startPositionGroup // Horizontal
            var endHorizontalSubject = endPositionGroup - 2 // Horizontal
            val sizeSubgroup = 2
            var countLineSubject = 0 // Counter

            // Go through two subgroups
            for (column in 1..2) {

                // Printing
                if (column == 2) {
                    println("|")
                }

                // Data for parity weeks
                var startVerticalSubject = startPositionSubject
                var endVerticalSubject = endPositionSubject - 3

                // Inside the subgroup, walk along parity weeks
                for (parity in 1..2) {
                    // Run along the line subject VERTICAL
                    for (lineSubject in startVerticalSubject..endVerticalSubject) {
                        //Run along the column subject Horizontal
                        for (width in startHorizontalSubject..endHorizontalSubject) {
                            // Printing values
                            val value = getValueCell(lineSubject, width)
                            if (value != null) {
                                println(value)
                            }
                        }
                        countLineSubject++
                    }
                    // Printing border
                    if (parity == 1) {
                        println("\n_____________")
                    }

                    // Next week
                    startVerticalSubject = endVerticalSubject + 1
                    endVerticalSubject += 3
                }
                // Next subgroup
                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }
            // Next subject
            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        when {
            // If there is a subgroups & parity weeks
            analyzer.checkSubgroupsWithParityWeek() -> {
                subgroupWorkWithParityWeek()
            }

            // If there is a subgroups
            analyzer.checkSubgroups() -> {
                subgroupWork()
            }

            // If there is a parity weeks
            analyzer.checkParityWeek() -> {
                defaultWorkWithParityWeek()
            }
            else -> {
                defaultWork()
            }
        }
    }

    private fun selectDay(numDay: Int) {
        for (numSubject in 1..sizeDay) {
            println("${MyColor.ANSI_PURPLE}Subject #$numSubject ${MyColor.ANSI_RESET}")
            selectSubject(numSubject) // SELECT SUBJECT
        }
    }

    fun selectGroup(startPositionGroup: Int) {
        // Work area initialization
        this.startPositionGroup = startPositionGroup
        endPositionGroup = startPositionGroup + sizeGroup

        nameGroup = getNameGroup(startPositionGroup)
        println(MyColor.ANSI_GREEN + nameGroup + MyColor.ANSI_RESET)

        // Start process
        if (nameGroup != null) {
            assembler
            for (numDay in 1.. countDay) {
                println("${MyColor.ANSI_BLUE}Day #$numDay ${MyColor.ANSI_RESET}")
                selectDay(numDay) // SELECT DAY
            }
        }
    }

    private fun getNameGroup(startPositionGroup: Int): String? {
        val name = getValueCell(positionNameGroup, startPositionGroup-2)
        return if (name is String) {
            name
        } else null
    }

    private fun getValueCell(heightPosition: Int, widthPosition: Int): Any? {
        val cell = sheet.getRow(heightPosition).getCell(widthPosition)
        if (cell.cellType == CellType.STRING) {
            return cell.stringCellValue
        }
        if (cell.cellType == CellType.NUMERIC) {
            return cell.numericCellValue
        }
        return null
    }

    fun getAssembler(): Assembler {
        return assembler
    }

}