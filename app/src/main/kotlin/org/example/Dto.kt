package org.example

import java.math.BigDecimal
import java.util.Date

data class Operation(
    val date: Date,
    val cost: BigDecimal,
    val category: String?,
    val description: String?,
)

data class Coords2D(
    val x: Float,
    val y: Float
) : Comparable<Coords2D> {
    override fun compareTo(other: Coords2D): Int {
        // Сначала сравниваем по x
        val xCompare = x.compareTo(other.x)
        if (xCompare != 0) return xCompare

        // Если x равны, сравниваем по y
        return y.compareTo(other.y)
    }
}