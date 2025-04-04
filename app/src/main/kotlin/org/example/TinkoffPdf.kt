package org.example

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.text.SimpleDateFormat

fun parseTinkoffDoc(path: String) {
    val reader = PdfReader(path)

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


        operations.forEach {
            println(it)
        }
    }

    reader.close()
}
