package ru.nsu.dbb.response

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableObjectValue
import ru.nsu.dbb.entity.explain_plan.StringTreeNode
import ru.nucodelabs.kfx.ext.getValue
import ru.nucodelabs.kfx.ext.setValue

class ExplainPlanResultPipe {
    private val _lastResponse: ObjectProperty<StringTreeNode> = SimpleObjectProperty()

    val observable: ObservableObjectValue<StringTreeNode>
        get() = _lastResponse

    private var lastResponse by _lastResponse

    fun pushItem(item: StringTreeNode) {
        lastResponse = item
    }
}