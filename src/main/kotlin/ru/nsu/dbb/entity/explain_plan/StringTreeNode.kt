package ru.nsu.dbb.entity.explain_plan

data class StringTreeNode (val columns: List<String>, val children: List<StringTreeNode>)