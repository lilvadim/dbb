package ru.nsu.dbb.view

import javafx.collections.MapChangeListener
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.entity.Database
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class QueryConsoleViewController @Inject constructor(
    private val queryConsoleController: ConsoleController,
    private val alertFactory: AlertFactory
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var textArea: TextArea

    @FXML
    private lateinit var databaseSelector: ComboBox<String>

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        queryConsoleController.databaseStorage.storage.addListener(MapChangeListener {
            if (it.wasAdded() && it.valueRemoved == null) {
                if (databaseSelector.items.isEmpty()) {
                    databaseSelector.value = it.valueAdded.name
                }
                databaseSelector.items.add(it.valueAdded.name)
            }
            if (it.wasRemoved()) {
                databaseSelector.items.remove(it.valueAdded.name)
            }
        })
    }

    @FXML
    private fun runQuery() {
        try {
            queryConsoleController.runQuery(databaseSelector.value, textArea.text)
        } catch (e: Exception) {
            alertFactory.simpleExceptionAlert(e, stage).show()
            e.printStackTrace()
        }
    }

}