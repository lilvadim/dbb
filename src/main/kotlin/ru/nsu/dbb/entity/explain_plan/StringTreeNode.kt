package ru.nsu.dbb.entity.explain_plan

data class StringTreeNode(
    /**
     * Mapping column name -> value
     */
    val columns: Map<String, String>,
    val children: MutableList<StringTreeNode> = arrayListOf(),
    val parent: StringTreeNode? = null
)