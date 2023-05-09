package ru.nsu.dbb.view.represent

import javafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import ru.nsu.dbb.sql.SqlOperationParameter

class ParametersFieldsGenerator {
    private fun createUiControl(sqlOperationParameter: SqlOperationParameter): Pair<Node, Property<*>> {
        return when (sqlOperationParameter.type) {
            String::class -> {
                val control = TextField()
                return Pair(control, control.textProperty())
            }

            Boolean::class -> {
                val control = CheckBox()
                return Pair(control, control.selectedProperty())
            }

            else -> throw IllegalStateException()
        }
    }

    fun generate(parameters: List<SqlOperationParameter>): Map<SqlOperationParameter, Pair<Node, Property<*>>> {
        val map = mutableMapOf<SqlOperationParameter, Pair<Node, Property<*>>>()

        parameters.forEach { param ->
            val label = Label(param.fullName)
            val box = HBox(label)
            val (control, prop) = createUiControl(param)
            when (param.type) {
                String::class -> {
                    box.children += control
                }

                Boolean::class -> {
                    box.children.add(0, control)
                }
            }
            map += Pair(param, Pair(control, prop))
        }

        return map
    }
}