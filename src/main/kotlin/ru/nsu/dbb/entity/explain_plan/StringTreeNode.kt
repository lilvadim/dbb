package ru.nsu.dbb.entity.explain_plan

data class StringTreeNode(
    /**
     * Mapping column name -> value
     */
    val columnNames: List<String>,
    val columnValues: MutableList<String>,
    val children: MutableList<StringTreeNode> = arrayListOf(),
    var parent: StringTreeNode? = null
)