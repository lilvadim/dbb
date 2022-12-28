package ru.nsu.dbb.entity.explain_plan

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TreeNodeTest {

    @Test
    fun initTreeTest() {
        var operation = Operation(
            "GroupAggregate", "", 185, 376.55, 808.28, "width=8"
        )
        val root = TreeNode(operation, arrayListOf(), null)
        Assertions.assertEquals(root.childNodes.size, 0)

        operation = Operation(
            "N1 name", "N1 params", 1, 1.0, 10.0, "width=1"
        )
        val treeNodeList = mutableListOf<TreeNode>()
        treeNodeList.add(TreeNode(operation, arrayListOf(), null))

        operation = Operation(
            "N2 name", "N2 params", 2, 2.0, 20.0, "width=2"
        )
        treeNodeList.add(TreeNode(operation, arrayListOf(), null))

        root.childNodes = treeNodeList
        Assertions.assertEquals(root.childNodes[0].operation.name, "N1 name")
        Assertions.assertEquals(root.childNodes[1].operation.name, "N2 name")
    }
}