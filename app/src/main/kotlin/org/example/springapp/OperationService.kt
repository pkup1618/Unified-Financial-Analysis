package org.example.springapp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.example.Operation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Date

@Service
class OperationService(private val jdbcTemplate: JdbcTemplate) {
    private val logger: Logger = LoggerFactory.getLogger(OperationService::class.java)

    fun getAllOperations(): List<Operation> {
        logger.info("Fetching all operations")
        return jdbcTemplate.query("SELECT * FROM operations") { rs, _ ->
            Operation(
                id = rs.getLong("id"),
                date = rs.getDate("date"),
                cost = rs.getBigDecimal("cost"),
                category = rs.getString("category"),
                description = rs.getString("description")
            )
        }.also { logger.info("Retrieved {} operations", it.size) }
    }

    fun getOperationById(id: Long): Operation? {
        logger.info("Fetching operation with id: {}", id)

        return jdbcTemplate.query("SELECT * FROM operations WHERE id = ?", id) { rs, _ ->
            Operation(
                id = rs.getLong("id"),
                date = rs.getDate("date"),
                cost = rs.getBigDecimal("cost"),
                category = rs.getString("category"),
                description = rs.getString("description")
            )
        }.firstOrNull().also {
            if (it == null) logger.info("No operation found with id: {}", id)
            else logger.info("Operation found with id: {}", id)
        }
    }

    fun getOperationByDateAndCost(date: Date, cost: BigDecimal): Operation? {
        val result = jdbcTemplate.query("SELECT * FROM operations WHERE date = ? AND cost = ?", date, cost) { rs, _ ->
            Operation(
                id = rs.getLong("id"),
                date = rs.getDate("date"),
                cost = rs.getBigDecimal("cost"),
                category = rs.getString("category"),
                description = rs.getString("description")
            )
        }.firstOrNull().also {
            if (it == null) logger.info("No operation found with date: {}, cost: {}", date, cost)
            else logger.info("Operation found with date: {}, cost: {}", date, cost)
        }

        return result
    }

    fun saveOperation(operation: Operation): Operation {
        val existingOperation = getOperationByDateAndCost(Date(operation.date.time), operation.cost)

        return if (existingOperation == null) createOperation(operation)
               else updateOperation(operation)
    }

    fun saveAllOperation(operations: List<Operation>): List<Operation> {
        logger.info("Saving {} operations", operations.size)
        return operations
            .map { operation -> saveOperation(operation) }
            .also { logger.info("Successfully saved {} operations", it.size) }
    }

    fun saveOperationsFromPdf(operations: List<Operation>): List<Operation> {
        logger.info("Saving {} operations from PDF", operations.size)
        return operations
            .map { saveOperation(it) }
            .also { logger.info("Successfully saved {} operations from PDF", it.size) }
    }

    private fun createOperation(operation: Operation): Operation {
        logger.info("Creating new operation: {}", operation)

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(
                "INSERT INTO operations (date, cost, category, description) VALUES (?, ?, ?, ?)",
                arrayOf("id")
            )
            ps.setDate(1, Date(operation.date.time))
            ps.setBigDecimal(2, operation.cost)
            ps.setString(3, operation.category)
            ps.setString(4, operation.description)
            ps
        }, keyHolder)
        val id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to retrieve generated ID")
            .also { logger.error("Failed to retrieve generated ID for operation: {}", operation) }
        return operation.copy(id = id)
    }

    private fun updateOperation(operation: Operation): Operation {
        logger.info("Updating operation: {}", operation)
        jdbcTemplate.update(
            "UPDATE operations SET category = ?, description = ? WHERE date = ? AND cost = ?",
            operation.category,
            operation.description,
            Date(operation.date.time),
            operation.cost,
        )
        return operation
    }

    fun deleteOperation(id: Long): Boolean {
        logger.info("Deleting operation with id: {}", id)
        val rowsAffected = jdbcTemplate.update("DELETE FROM operations WHERE id = ?", id)

        if (rowsAffected > 0)  {
            logger.info("Successfully deleted operation with id: {}", id)
        } else {
            logger.info("No operation found to delete with id: {}", id)
        }

        return rowsAffected > 0
    }
}