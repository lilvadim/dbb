package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.fxml.FXML
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nsu.dbb.entity.DatabaseStorage
import ru.nsu.dbb.view.represent.ExplorerItem
import ru.nsu.dbb.view.represent.mapper.ExplorerMapper
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class DatabaseExplorerViewController @Inject constructor(
    private val mapper: ExplorerMapper,
    private val databaseConnectivityController: DatabaseConnectivityController
) : AbstractViewController<VBox>() {

    private val databaseStorage: DatabaseStorage = databaseConnectivityController.databaseStorage

    @FXML
    private lateinit var treeView: TreeView<ExplorerItem>

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        databaseStorage.storage.addListener(MapChangeListener {
            updateView()
            observeCatalogs()
        })
        observeCatalogs()
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