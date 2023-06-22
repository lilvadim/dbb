package ru.nsu.dbb.view

import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.sql.DdlQueryBuilder
import ru.nsu.dbb.sql.ddl.DdlOperationParameter
import ru.nsu.dbb.sql.ddl.DdlOperationType
import ru.nsu.dbb.sql.ddl.createStatement
import ru.nsu.dbb.view.context.DatabaseContext
import ru.nsu.dbb.view.represent.ParametersFieldsGenerator
import ru.nucodelabs.kfx.core.AbstractViewController
import javax.inject.Inject

class ModifyTableScreenController @Inject constructor(
    private val consoleController: ConsoleController,
    private val parametersFieldsGenerator: ParametersFieldsGenerator,
    private val ddlQueryBuilder: DdlQueryBuilder,
    private val databaseContext: DatabaseContext,
) : AbstractViewController<VBox>() {

    @FXML
    private lateinit var dbName: Label

    @FXML
    private lateinit var operationDescription: Label

    @FXML
    private lateinit var queryText: TextArea

    @FXML
    private lateinit var container: VBox

    private lateinit var ddlOperationType: DdlOperationType
    private lateinit var fieldsMap: Map<DdlOperationParameter, Pair<Node, ObservableValue<out Any>>>

    @FXML
    private fun apply() {
        consoleController.runQuery(databaseContext.database?.name, query())
    }

    fun initForOperation(ddlOperationType: DdlOperationType) {
        this.ddlOperationType = ddlOperationType
        dbName.text = "Database: ${databaseContext.database?.name}"
        operationDescription.text = ddlOperationType.description
        fieldsMap = parametersFieldsGenerator.generate(ddlOperationType.parameters)
        fieldsMap.values.map { it.second }.forEach {
            it.addListener { _, _, _ ->
                queryText.text = query()
            }
        }
        container.children.setAll(fieldsMap.values.map { it.first })
    }

    private fun query(): String {
        return ddlQueryBuilder.createStatement(
            ddlOperationType,
            fieldsMap.map { (param, prop) -> param to prop.second.value }.toMap()
        )
    }
}
