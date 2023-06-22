package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import javafx.util.Callback
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nsu.dbb.entity.DatabaseStorage
import ru.nsu.dbb.entity.Table
import ru.nsu.dbb.view.represent.ExplorerItem
import ru.nsu.dbb.view.represent.ParametersFieldsGenerator
import ru.nsu.dbb.view.represent.mapper.ExplorerMapper
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

const val SELECT_TABLE_TEMPLATE = "SELECT * FROM %s"

class DatabaseExplorerViewController @Inject constructor(
    private val mapper: ExplorerMapper,
    private val databaseConnectivityController: DatabaseConnectivityController,
    private val consoleController: ConsoleController,
    private val parametersFieldsGenerator: ParametersFieldsGenerator
) : AbstractViewController<VBox>() {

    private val databaseStorage: DatabaseStorage
        get() = databaseConnectivityController.databaseStorage

    @FXML
    private lateinit var treeView: TreeView<ExplorerItem>

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        observeStorage()
        observeCatalogs()
        treeView.cellFactory = Callback {
            TreeCell<ExplorerItem>().apply {
                textProperty().bind(itemProperty().map { it.label })
                contextMenu = ContextMenu(
                    MenuItem().apply {
                        textProperty().bind(itemProperty().map {
                            it.reference::class.qualifiedName
                        })
                    }
                )
                onMouseClicked = EventHandler { e ->
                    val obj = item.reference
                    if (e.clickCount == 2 && obj is Table) {
                        consoleController.runQuery(SELECT_TABLE_TEMPLATE.format(obj.name))
                    }
                }
            }
        }
    }

    private fun observeStorage() {
        databaseStorage.storage.addListener(MapChangeListener {
            updateView()
            observeCatalogs()
        })
    }

    private val updater = ListChangeListener<Any> {
        while (it.next()) {
            updateView()
        }
    }

    private fun observeCatalogs() {
        databaseStorage.storage.values.forEach { db -> db.catalogs.removeListener(updater) }
        databaseStorage.storage.values.forEach { db -> db.catalogs.addListener(updater) }
    }

    @FXML
    private fun refreshAll() {
        databaseStorage.storage.keys.forEach { databaseConnectivityController.refreshDatabase(it) }
        updateView()
    }

    private fun updateView() {
        treeView.root = mapper.mapToTreeView(databaseStorage)
    }
}