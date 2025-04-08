package org.example.springapp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.example.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/operations")
class OperationController(
    private val operationService: OperationService,
    private val pdfParsingService: PdfParsingService,
) {
    private val logger: Logger = LoggerFactory.getLogger(OperationController::class.java)

    @GetMapping
    fun getAllOperations(): List<Operation> {
        logger.info("GET request for all operations")
        return operationService.getAllOperations()
    }

    @GetMapping("/parseSber")
    fun parseSber(): List<Operation> {
        logger.info("GET request for parsing SBER operations")
        return pdfParsingService.parseAndSaveSberDoc()
    }

    @GetMapping("/parseTinkoff")
    fun parseTinkoff(): List<Operation> {
        logger.info("GET request for parsing TINKOFF operations")
        return pdfParsingService.parseAndSaveTinkoffDoc()
    }


    @GetMapping("/{id}")
    fun getOperationById(@PathVariable id: Long): ResponseEntity<Operation> {
        logger.info("GET request for operation with id: {}", id)
        val operation = operationService.getOperationById(id)
        return if (operation != null) {
            ResponseEntity.ok(operation)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createOperation(@RequestBody operation: Operation): Operation {
        logger.info("POST request to create operation: {}", operation)
        return operationService.saveOperation(operation)
    }

    @PutMapping("/{id}")
    fun updateOperation(@PathVariable id: Long, @RequestBody operation: Operation): ResponseEntity<Operation> {
        logger.info("PUT request to update operation with id: {}, data: {}", id, operation)
        if (operation.id != id) {
            logger.warn("Operation id {} doesn't match path id {}", operation.id, id)
            return ResponseEntity.badRequest().build()
        }
        val updated = operationService.saveOperation(operation)
        return ResponseEntity.ok(updated)
    }

    @PostMapping("/upload-pdf")
    fun uploadPdf(@RequestParam("file") file: MultipartFile): ResponseEntity<List<Operation>> {
        logger.info("POST request to upload PDF file: {}", file.originalFilename)
        val operations = listOf<Operation>() // TODO: Implement PDF parsing logic
        val savedOperations = operationService.saveOperationsFromPdf(operations)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperations)
    }

    @DeleteMapping("/{id}")
    fun deleteOperation(@PathVariable id: Long): ResponseEntity<Void> {
        logger.info("DELETE request for operation with id: {}", id)
        return if (operationService.deleteOperation(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}