package org.example.springapp

import org.example.Operation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class OperationService(private val jdbcTemplate: JdbcTemplate) {

    fun getAllOperations(): List<Operation> {
        return jdbcTemplate.query("SELECT * FROM operations") { rs, _ ->
            Operation(
                id = rs.getLong("id"),
                date = rs.getDate("date"),
                cost = rs.getBigDecimal("cost"),
                category = rs.getString("category"),
                description = rs.getString("description")
            )
        }
    }

    fun getOperationById(id: Long): Operation? {
        return jdbcTemplate.query("SELECT * FROM operations WHERE id = ?", id) { rs, _ ->
            Operation(
                id = rs.getLong("id"),
                date = rs.getDate("date"),
                cost = rs.getBigDecimal("cost"),
                category = rs.getString("category"),
                description = rs.getString("description")
            )
        }.firstOrNull()
    }

    fun saveOperation(operation: Operation): Operation {
        return if (operation.id == null) {
            createOperation(operation)
        } else {
            updateOperation(operation)
        }
    }

    fun saveAllOperation(operations: List<Operation>): List<Operation> {
        return operations.map { operation ->
            if (operation.id == null) {
                createOperation(operation)
            } else {
                updateOperation(operation)
            }
        }
    }

    fun deleteOperation(id: Long): Boolean {
        val rowsAffected = jdbcTemplate.update("DELETE FROM operations WHERE id = ?", id)
        return rowsAffected > 0
    }

    private fun createOperation(operation: Operation): Operation {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(
                "INSERT INTO operations (date, cost, category, description) VALUES (?, ?, ?, ?)",
                arrayOf("id") // Указываем, что хотим вернуть только столбец "id"
            )
            ps.setDate(1, Date(operation.date.time))
            ps.setBigDecimal(2, operation.cost)
            ps.setString(3, operation.category)
            ps.setString(4, operation.description)
            ps
        }, keyHolder)
        val id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to retrieve generated ID")
        return operation.copy(id = id)
    }

    private fun updateOperation(operation: Operation): Operation {
        jdbcTemplate.update(
            "UPDATE operations SET date = ?, cost = ?, category = ?, description = ? WHERE id = ?",
            Date(operation.date.time),
            operation.cost,
            operation.category,
            operation.description,
            operation.id
        )
        return operation
    }

    fun saveOperationsFromPdf(operations: List<Operation>): List<Operation> {
        return operations.map { createOperation(it) }
    }
}