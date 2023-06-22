package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.fxml.FXML
import javafx.scene.control.TreeItem
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
        observeStorage()
        observeCatalogs()
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
        val oldRoot = treeView.root
        val newRoot = mapper.mapToTreeView(databaseStorage)
        restoreExpandedItems(newRoot, oldRoot)
        treeView.root = newRoot
    }

    private fun restoreExpandedItems(newItem: TreeItem<ExplorerItem>, oldItem: TreeItem<ExplorerItem>?) {
        if (oldItem == null) {
            return
        }
        val oldChildrenByLabel = oldItem.children.associateBy { it.value.label }
        newItem.isExpanded = oldItem.isExpanded
        newItem.children.forEach { item ->
            restoreExpandedItems(item, oldChildrenByLabel[item.value.label])
        }
    }
}