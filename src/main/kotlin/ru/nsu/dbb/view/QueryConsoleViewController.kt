package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.ConsoleController
import ru.nucodelabs.kfx.core.AbstractViewController
import javax.inject.Inject

class QueryConsoleViewController @Inject constructor(
    private val queryConsoleController: ConsoleController,
    private val alertFactory: AlertFactory
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var textArea: TextArea

    @FXML
    private fun runQuery() {
        try {
            queryConsoleController.runQuery(textArea.text)
        } catch (e: Exception) {
            alertFactory.simpleExceptionAlert(e, stage).show()
            e.printStackTrace()
        }
    }

}