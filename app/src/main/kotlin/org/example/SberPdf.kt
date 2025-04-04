package org.example

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.text.SimpleDateFormat

fun main(args: Array<String>) {
    printDoc("docs/tinkoff-pdf.pdf")
//    printDoc("docs/sber-pdf.pdf")
}

fun parseSberDoc(path: String) {
    val reader = PdfReader(path)

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

        operations.forEach {
            println(it)
        }
    }

    reader.close()
}
