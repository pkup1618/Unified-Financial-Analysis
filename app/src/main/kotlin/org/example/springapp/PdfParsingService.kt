package org.example.springapp

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import org.example.Operation
import org.example.TextExtractionStrategyImpl
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class PdfParsingService(
    private val operationService: OperationService
) {
    fun parseAndSaveSberDoc(): List<Operation> {
        val operations = parseSberDoc("sber-pdf.pdf")

        return operationService.saveAllOperation(operations)
    }

    fun parseAndSaveTinkoffDoc(): List<Operation> {
        val operations = parseTinkoffDoc("tinkoff-pdf.pdf")

        return operationService.saveAllOperation(operations)
    }

    fun parseSberDoc(path: String): List<Operation> {
        val reader = PdfReader(path)

        val allOperations = mutableListOf<Operation>()

        for (i in 1..reader.numberOfPages) {
            val strategy = TextExtractionStrategyImpl()
            PdfTextExtractor.getTextFromPage(reader, i, strategy)

            val entries = strategy.getStringsWithCoordinates()

            val usefulEntriesHeights = entries.filterValues { it.matches(Regex("^\\d{6}\$")) }.map { it.key.y }
            val usefulEntries = entries.filterKeys { it.x < 530 }.filterKeys { it.y in usefulEntriesHeights }
            val groupedStrings = usefulEntries.entries.groupBy { it.key.y }

            val operations = groupedStrings.map { height ->
                val objectAsArray = height.value.map { it.value }

                Operation(
                    null,
                    SimpleDateFormat("dd.MM.yyyy HH:mm").parse("${objectAsArray[0]} ${objectAsArray[1]}"),
                    objectAsArray[4]
                        .replace("\\u00A0".toRegex(), "")
                        .replace(",".toRegex(), ".")
                        .replace("\\+".toRegex(), "-")
                        .toBigDecimal()
                        .negate(),
                    objectAsArray[3],
                    null,
                )
            }

            allOperations.addAll(operations)
        }

        reader.close()

        return allOperations
    }


    fun parseTinkoffDoc(path: String): List<Operation> {
        val reader = PdfReader(path)

        val allOperations = mutableListOf<Operation>()

        for (i in 1..reader.numberOfPages) {
            val strategy = TextExtractionStrategyImpl()
            PdfTextExtractor.getTextFromPage(reader, i, strategy)

            val entries = strategy.getStringsWithCoordinates()
            val usefulEntriesHeightsPairs = entries
                .filterKeys { it.x > 100 && it.x < 150 }
                .filterValues {
                    it.matches(Regex("^\\d\\d:\\d\\d\$")) or it.matches(Regex("^\\d\\d.\\d\\d.\\d\\d\\d\\d\$"))
                }
                .map { it.key.y }
                .chunked(2)

            val operationEntries = usefulEntriesHeightsPairs.map { heightPair ->
                entries.filterKeys { it.y in heightPair }
            }

            // forming object
            val operations = operationEntries.map { entries ->
                val datePart = entries
                    .filterKeys { it.x > 50 && it.x < 60 }
                    .filterValues { it.matches(Regex("^\\d\\d.\\d\\d.\\d\\d\\d\\d\$")) }
                    .map { it.value }
                    .first()

                val timePart = entries
                    .filterKeys { it.x > 50 && it.x < 60 }
                    .filterValues { it.matches(Regex("^\\d\\d:\\d\\d$")) }
                    .map { it.value }
                    .first()

                val date = SimpleDateFormat("dd.MM.yyyy HH:mm").parse("$datePart $timePart")

                val cost = entries
                    .filterKeys { it.x > 190 && it.x < 210 }
                    .map { it.value
                        .replace(" ".toRegex(), "")
                        .toBigDecimal()
                    }
                    .first()

                val description = entries
                    .filterKeys { it.x > 380 && it.x < 400 }
                    .toSortedMap()
                    .map { it.value }
                    .reversed()
                    .joinToString(" ")

                Operation(
                    id = null,
                    date = date,
                    cost = cost,
                    category = null,
                    description = description
                )
            }

            allOperations.addAll(operations)
        }

        reader.close()

        return allOperations
    }
}