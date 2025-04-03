package org.example

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.*
import com.itextpdf.text.pdf.parser.Vector
import java.util.*

fun main(args: Array<String>) {
//    printDoc("docs/sber-pdf.pdf")
//    printDoc("docs/tinkoff-pdf.pdf")
    parseTinkoffDoc("docs/tinkoff-pdf.pdf")
}

fun printDoc(path: String) {
    val reader = PdfReader(path)

    for (i in 1..reader.numberOfPages) {
        val strategy = TextExtractionStrategyImpl()

        // вызываем, чтобы наша реализация стратегия получила информацию о тексте на странице
        PdfTextExtractor.getTextFromPage(reader, i, strategy)
        println("Page : $i")

        for (entry in strategy.getStringsWithCoordinates()) {
            println("----------------")
            println("(x: ${entry.key.x}, y: ${entry.key.y})")
            println(entry.value)
        }
    }

    reader.close()
}


class TextExtractionStrategyImpl : TextExtractionStrategy {
    private val textMap = mutableMapOf<Float, MutableMap<Float, String>>()

    override fun getResultantText(): String {
        val stringBuilder = StringBuilder()
        val sortedTextWithCoords = textMap.mapValues { it.value.toSortedMap() }.toSortedMap()

        sortedTextWithCoords.forEach { x ->
            x.value.forEach { y ->
                stringBuilder.append(y.value)
            }
        }

        return stringBuilder.toString()
    }

    override fun renderText(renderInfo: TextRenderInfo) {
        // вытаскиваем координаты
        val x = renderInfo.baseline.startPoint[Vector.I2]
        val y = renderInfo.baseline.startPoint[Vector.I1]

        // если до этого мы не добавляли элементы из этой строчки файла.
        if (!textMap.containsKey(y)) {
            textMap[y] = TreeMap()
        }

        textMap[y]?.set(x, renderInfo.text)
    }

    /**
     * Отсортированные текстовые записи по координатной сетке (сначала по x, потом по y)
     */
    fun getStringsWithCoordinates(): SortedMap<Coords2D, String> {
        val result = mutableMapOf<Coords2D, String>()
        textMap.forEach { x ->
            x.value.forEach { y ->
                result[Coords2D(x.key, y.key)] = y.value
            }
        }

        return result.toSortedMap()
    }

    override fun beginTextBlock() {}

    override fun renderImage(imageRenderInfo: ImageRenderInfo?) {}

    override fun endTextBlock() {}
}
