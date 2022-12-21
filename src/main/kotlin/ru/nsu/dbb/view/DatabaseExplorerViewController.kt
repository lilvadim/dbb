package ru.nsu.dbb.view

import javafx.collections.MapChangeListener
import javafx.fxml.FXML
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nsu.dbb.entity.DatabaseStorage
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class DatabaseExplorerViewController @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val parser: DatabaseStorageToTreeParser,
    private val databaseConnectivityController: DatabaseConnectivityController
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var treeView: TreeView<String>

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        databaseStorage.storage.addListener(MapChangeListener {
            treeView.root = parser.parse(databaseStorage)
        })
    }

    @FXML
    private fun refreshAll() {
        databaseStorage.storage.keys.forEach { databaseConnectivityController.refreshDatabase(it) }
    }
}