package ru.nsu.dbb.entity.explain_plan

data class Operation(
    val name: String,
    val params: String,
    val rows: Int?,
    val startupCost: Double?,
    val totalCost: Double?,
    var desc: String
)