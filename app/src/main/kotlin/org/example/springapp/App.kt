package org.example.springapp

import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {
    val ctx = runApplication<App>(*args)

    val parsingService = ctx.getBean<PdfParsingService>()
    parsingService.parseAndSaveSberDoc()
}