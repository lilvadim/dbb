package ru.nsu.dbb.view

import javafx.collections.MapChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.entity.Database
import ru.nsu.dbb.view.context.DatabaseContext
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class QueryConsoleViewController @Inject constructor(
    private val queryConsoleController: ConsoleController,
    private val alertFactory: AlertFactory,
    private val databaseContext: DatabaseContext,
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var textArea: TextArea

    @FXML
    private lateinit var databaseSelector: ComboBox<Database>

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        textArea.onKeyPressed = EventHandler { e ->
            if (e.isShortcutDown && e.code == KeyCode.ENTER) {
                runQuery()
            }
        }
        queryConsoleController.databaseStorage.storage.addListener(MapChangeListener {
            if (it.wasAdded() && it.valueRemoved == null) {
                if (databaseSelector.items.isEmpty()) {
                    databaseSelector.value = it.valueAdded
                }
                databaseSelector.items.add(it.valueAdded)
            }
            if (it.wasRemoved()) {
                databaseSelector.items.remove(it.valueAdded)
            }
        })
        databaseSelector.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                databaseContext.database = newValue
            }
        }
        databaseContext.databaseProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                databaseSelector.selectionModel.select(newValue)
            }
        }
    }

    @FXML
    private fun runQuery() {
        try {
            queryConsoleController.runQuery(databaseSelector.value.name, textArea.text)
        } catch (e: Exception) {
            alertFactory.simpleExceptionAlert(e, stage).show()
            e.printStackTrace()
        }
    }

}