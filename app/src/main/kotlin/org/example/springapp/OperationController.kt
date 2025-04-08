package org.example.springapp


import org.example.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/operations")
class OperationController(private val operationService: OperationService) {

    @GetMapping
    fun getAllOperations(): List<Operation> {
        return operationService.getAllOperations()
    }

    @GetMapping("/{date}{cost}")
    fun getOperationById(@PathVariable id: Long): ResponseEntity<Operation> {
        val operation = operationService.getOperationById(id)
        return if (operation != null) {
            ResponseEntity.ok(operation)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createOperation(@RequestBody operation: Operation): Operation {
        return operationService.saveOperation(operation)
    }

    @PutMapping("/{id}")
    fun updateOperation(@PathVariable id: Long, @RequestBody operation: Operation): ResponseEntity<Operation> {
        if (operation.id != id) {
            return ResponseEntity.badRequest().build()
        }
        val updated = operationService.saveOperation(operation)
        return ResponseEntity.ok(updated)
    }

    @PostMapping("/upload-pdf")
    fun uploadPdf(@RequestParam("file") file: MultipartFile): ResponseEntity<List<Operation>> {
        val operations = listOf<Operation>() // TODO
        val savedOperations = operationService.saveOperationsFromPdf(operations)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperations)
    }

    @DeleteMapping("/{id}")
    fun deleteOperation(@PathVariable id: Long): ResponseEntity<Void> {
        return if (operationService.deleteOperation(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}