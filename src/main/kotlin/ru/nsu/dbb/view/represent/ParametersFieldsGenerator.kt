package ru.nsu.dbb.view.represent

import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import ru.nsu.dbb.sql.ddl.DdlOperationParameter

class ParametersFieldsGenerator {
    @Suppress("UNCHECKED_CAST")
    private fun createUiControl(ddlOperationParameter: DdlOperationParameter): Pair<Node, ObservableValue<out Any>> {
        return when (ddlOperationParameter.type) {
            String::class -> {
                val control = TextField()
                Pair(control, control.textProperty())
            }

            Boolean::class -> {
                val control = CheckBox()
                Pair(control, control.selectedProperty())
            }

            List::class -> {
                val control = TextField()
                val prop = control.textProperty().map { it.split(',').map { it.trim() } }
                Pair(control, prop)
            }

            else -> throw IllegalStateException()
        }
    }

    fun generate(parameters: List<DdlOperationParameter>): Map<DdlOperationParameter, Pair<Node, ObservableValue<out Any>>> {
        val map = mutableMapOf<DdlOperationParameter, Pair<Node, ObservableValue<out Any>>>()

        parameters.forEach { param ->
            val label = Label(param.fullName)
            val box = HBox(label).apply {
                alignment = Pos.CENTER_LEFT
                spacing = 5.0
            }
            val (control, prop) = createUiControl(param)
            box.children += control
            map += Pair(param, Pair(box, prop))
        }

        return map
    }
}