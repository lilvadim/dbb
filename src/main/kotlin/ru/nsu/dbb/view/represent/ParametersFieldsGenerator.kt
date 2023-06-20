package ru.nsu.dbb.view.represent

import javafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import ru.nsu.dbb.sql.SqlOperationParameter

class ParametersFieldsGenerator {
    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> createUiControl(sqlOperationParameter: SqlOperationParameter): Pair<Node, Property<T>> {
        return when (sqlOperationParameter.type) {
            String::class -> {
                val control = TextField()
                Pair(control, control.textProperty()) as Pair<Node, Property<T>>
            }

            Boolean::class -> {
                val control = CheckBox()
                Pair(control, control.selectedProperty()) as Pair<Node, Property<T>>
            }

            else -> throw IllegalStateException()
        }
    }

    fun <T : Any> generate(parameters: List<SqlOperationParameter>): Map<SqlOperationParameter, Pair<Node, Property<T>>> {
        val map = mutableMapOf<SqlOperationParameter, Pair<Node, Property<T>>>()

        parameters.forEach { param ->
            val label = Label(param.fullName)
            val box = HBox(label)
            val (control, prop) = createUiControl<T>(param)
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