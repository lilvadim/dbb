package ru.nsu.dbb.entity.explain_plan

data class StringTreeNode(
    /**
     * Mapping column name -> value
     */
    val columnNames: List<String>,
    val columnValues: List<String>,
    val children: MutableList<StringTreeNode> = arrayListOf(),
    val parent: StringTreeNode? = null
)