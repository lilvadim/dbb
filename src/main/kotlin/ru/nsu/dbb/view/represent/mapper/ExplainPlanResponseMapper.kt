package ru.nsu.dbb.view.represent.mapper

import javafx.scene.control.TreeItem
import ru.nsu.dbb.entity.explain_plan.StringTreeNode

class ExplainPlanResponseMapper {
    fun mapToTeeView(root: StringTreeNode): TreeItem<StringTreeNode> {
        return TreeItem(root).apply {
            isExpanded = true
            children.addAll(root.children.map { mapToTeeView(it) })
        }
    }
}