package ru.nsu.dbb.entity.explain_plan

data class TreeNode(val operation: Operation, var childNodes: MutableList<TreeNode> = mutableListOf())