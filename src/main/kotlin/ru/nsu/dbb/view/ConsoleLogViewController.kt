package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import ru.nsu.dbb.entity.ConsoleLog
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class ConsoleLogViewController @Inject constructor(
    private val consoleLog: ConsoleLog
) : AbstractViewController<VBox>() {

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
        container.children.add(Label(consoleLog.logs.last()))
    }
}