package org.example

import java.math.BigDecimal
import java.util.Date

data class Operation(
    val date: Date,
    val cost: BigDecimal,
    val category: String?,
    val description: String?,
)