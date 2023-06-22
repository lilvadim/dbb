package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.entity.ConsoleLog
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class ConsoleLogViewController @Inject constructor(
    consoleController: ConsoleController
) : AbstractViewController<VBox>() {

    private val consoleLog: ConsoleLog = consoleController.consoleLog

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        consoleLog.logs.addListener(ListChangeListener { c ->
            while (c.next()) {
                printLog()
            }
        })
    }

    @FXML
    private lateinit var container: VBox

    private fun printLog() {
        container.children +=
            TextField(consoleLog.logs.last()).apply {
                style = """
                    -fx-background-color: transparent ;
                    -fx-background-insets: 0px ;
                    -fx-font-family: 'monospace';
                """.trimIndent()
                isEditable = false
                maxWidth = Double.MAX_VALUE
                prefWidth = container.width
                prefWidthProperty().bind(container.widthProperty())
            }
    }
}