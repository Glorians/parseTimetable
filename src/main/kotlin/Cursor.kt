import model.Subject
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import utils.Checker
import utils.MyColor
import utils.getMapNamesDays

class Cursor(private val sheet: Sheet, private val assembler: Assembler) {

    private val countDay = 6
    private var nameGroup: String? = null
    private var startPositionGroup = 0 // Horizontal
    private var endPositionGroup = 0 // Horizontal
    private var startPositionSubject = 2 // Vertical
    private var endPositionSubject = 7 // Vertical

    private val positionNameGroup = 0 // Horizontal
    private val sizeGroup = 3 // Horizontal
    private val sizeDay = 8 // Vertical
    private val sizeSubject = 5 // Vertical
    private val mapNamesDays = getMapNamesDays()

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

    private fun selectSubject(numSubject: Int, numDay: Int) {

        val analyzer = scanSubject() // the object stores information about the subject

        // If the most common subject, without subgroups and prime weeks
        fun defaultWork() {
            val listValue = arrayListOf<Any>()
            //Run along the line subject VERTICAL
            for (lineSubject in startPositionSubject..endPositionSubject) {
                //Run along the column subject Horizontal
                for (width in startPositionGroup..endPositionGroup) {
                    // Printing values
                    val value = getValueCell(lineSubject, width)
                    if (value != null) {
                        listValue.add(value)
                    }
                }
                startPositionSubject++
            }
            assembler.statusDefault()
            assembler.createSubject(listValue)
            listValue.clear()
            endPositionSubject = startPositionSubject + sizeSubject // End position of the next subject
        }

        // If the subject has subgroups
        fun subgroupWork() {
            val listValue = arrayListOf<Any>()
            var startHorizontalSubject = startPositionGroup // Horizontal
            var endHorizontalSubject = endPositionGroup - 2 // Horizontal
            var countLineSubject = 0 // Counter
            val sizeSubgroup = 2

            // Go through two subgroups
            for (column in 1..2) {

                when (column) {
                    1 -> assembler.currentSubgroup = "А"
                    2 -> assembler.currentSubgroup = "Б"
                }

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
                            listValue.add(value)
                        }
                    }
                    countLineSubject++
                }
                // Transition to the next subgroup
                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }
            assembler.statusSubgroups()
            assembler.createSubject(listValue)
            // Transition to the next subject
            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        // If subject with parity weeks
        fun defaultWorkWithParityWeek() {
            var startVerticalSubject = startPositionSubject // Vertical
            var endVerticalSubject = endPositionSubject - 3 // Vertical

            // We go along parity weeks
            for (parity in 1..2) {
                val listValue = arrayListOf<Any>()
                assembler.currentWeek = parity
                // Run along the line subject VERTICAL
                for (lineSubject in startVerticalSubject..endVerticalSubject) {
                    //Run along the column subject Horizontal
                    for (width in startPositionGroup..endPositionGroup) {
                        // Printing values
                        val value = getValueCell(lineSubject, width)
                        if (value != null) {
                            listValue.add(value)
                        }
                    }
                    startPositionSubject++
                }
                assembler.createSubject(listValue)
                // Next week
                startVerticalSubject = endVerticalSubject + 1
                endVerticalSubject += 3
            }
            assembler.statusParityWeek()

            endPositionSubject = startPositionSubject + sizeSubject // End position next subject
        }

        // If the subject has both subgroups and parity weeks
        fun subgroupWorkWithParityWeek() {
            val listValue = arrayListOf<Any>()
            // Data for subgroup
            var startHorizontalSubject = startPositionGroup // Horizontal
            var endHorizontalSubject = endPositionGroup - 2 // Horizontal
            val sizeSubgroup = 2
            var countLineSubject = 0 // Counter

            // Go through two subgroups
            for (column in 1..2) {

                when (column) {
                    1 -> assembler.currentSubgroup = "А"
                    2 -> assembler.currentSubgroup = "Б"
                }

                // Data for parity weeks
                var startVerticalSubject = startPositionSubject
                var endVerticalSubject = endPositionSubject - 3

                // Inside the subgroup, walk along parity weeks
                for (parity in 1..2) {
                    assembler.currentWeek = parity
                    // Run along the line subject VERTICAL
                    for (lineSubject in startVerticalSubject..endVerticalSubject) {
                        //Run along the column subject Horizontal
                        for (width in startHorizontalSubject..endHorizontalSubject) {
                            // Printing values
                            val value = getValueCell(lineSubject, width)
                            if (value != null) {
                                listValue.add(value)
                            }
                        }
                        countLineSubject++
                    }

                    // Next week
                    startVerticalSubject = endVerticalSubject + 1
                    endVerticalSubject += 3
                }
                // Next subgroup
                startHorizontalSubject += sizeSubgroup
                endHorizontalSubject += sizeSubgroup
            }
            assembler.statusParityAndSubgroup()
            assembler.createSubject(listValue)
            // Next subject
            startPositionSubject += countLineSubject / 2
            endPositionSubject = startPositionSubject + sizeSubject
        }

        when {
            // If there is a subgroups & parity weeks
            analyzer.checkSubgroupsWithParityWeek() -> {
                assembler.statusParityAndSubgroup()
                subgroupWorkWithParityWeek()
            }

            // If there is a subgroups
            analyzer.checkSubgroups() -> {
                assembler.statusSubgroups()
                subgroupWork()
            }

            // If there is a parity weeks
            analyzer.checkParityWeek() -> {
                assembler.statusParityWeek()
                defaultWorkWithParityWeek()
            }
            else -> {
                assembler.statusDefault()
                defaultWork()
            }
        }
    }

    private fun selectDay(numDay: Int) {
        for (numSubject in 1..sizeDay) {
            assembler.currentPositionSubject = numSubject
            assembler.clearStatus()
            selectSubject(numSubject, numDay) // SELECT SUBJECT
        }
    }

    fun selectGroup(startPositionGroup: Int) {
        // Work area initialization
        this.startPositionGroup = startPositionGroup
        endPositionGroup = startPositionGroup + sizeGroup

        nameGroup = getNameGroup(startPositionGroup)

        // Start process
        if (nameGroup != null) {
            assembler.createGroup(nameGroup!!)
            for (numDay in 1.. countDay) {
                assembler.currentNameDay = mapNamesDays[numDay]!!
                selectDay(numDay) // SELECT DAY
            }
            assembler.printingGroup()
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