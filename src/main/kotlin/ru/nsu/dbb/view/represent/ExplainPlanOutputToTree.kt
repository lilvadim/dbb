package ru.nsu.dbb.view.represent

import javafx.scene.control.TreeItem
import ru.nsu.dbb.entity.explain_plan.StringTreeNode

class ExplainPlanOutputToTree {
    fun convert(root: StringTreeNode): TreeItem<StringTreeNode> {
        return TreeItem(root).apply {
            isExpanded = true
            children.addAll(root.children.map { convert(it) })
        }
    }
}