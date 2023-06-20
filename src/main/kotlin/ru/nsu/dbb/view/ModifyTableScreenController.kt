package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.sql.SqlOperationType
import ru.nucodelabs.kfx.core.AbstractViewController
import javax.inject.Inject

class ModifyTableScreenController @Inject constructor(
    private val consoleController: ConsoleController
) : AbstractViewController<VBox>() {

    @FXML
    private lateinit var operationDescription: Label

    @FXML
    private lateinit var queryText: TextArea

    @FXML
    private fun apply() {
        consoleController.runQuery(queryText.text)
    }

    fun operation(sqlOperationType: SqlOperationType) {
        operationDescription.text = sqlOperationType.description
    }
}
