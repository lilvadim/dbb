package ru.nsu.dbb.view

import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import ru.nsu.dbb.controller.ConsoleController
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nsu.dbb.entity.*
import ru.nsu.dbb.sql.ddl.DdlOperationType
import ru.nsu.dbb.view.context.DatabaseContext
import ru.nsu.dbb.view.represent.ExplorerItem
import ru.nsu.dbb.view.represent.mapper.ExplorerMapper
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

const val SELECT_TABLE_TEMPLATE = "SELECT * FROM %s"

class DatabaseExplorerViewController @Inject constructor(
    private val mapper: ExplorerMapper,
    private val databaseConnectivityController: DatabaseConnectivityController,
    private val consoleController: ConsoleController,
    private val databaseContext: DatabaseContext,
) : AbstractViewController<VBox>() {

    private val databaseStorage: DatabaseStorage
        get() = databaseConnectivityController.databaseStorage

    @FXML
    private lateinit var treeView: TreeView<ExplorerItem>

    @FXML
    private lateinit var modifyTableScreen: Stage

    @FXML
    private lateinit var modifyTableScreenController: ModifyTableScreenController

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        observeStorage()
        observeCatalogs()
        modifyTableScreen.initOwner(stage)
        treeView.cellFactory = Callback {
            TreeCell<ExplorerItem>().apply {
                textProperty().bind(itemProperty().map { it.label })
                val ddls = treeItemProperty().map { it.value }.map { ddlQueryForItem(it.reference::class) }
                ddls.addListener { _, _, new ->
                    if (new != null && new.isNotEmpty()) {
                        contextMenu = ContextMenu().apply {
                            items.setAll(
                                new.map {
                                    MenuItem(it.description).apply {
                                        onAction = EventHandler { e ->
                                            modifyTableScreenController.initForOperation(it)
                                            modifyTableScreen.show()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
                onMouseClicked = EventHandler { e ->
                    if (item != null) {
                        when (e.clickCount) {
                            1 -> databaseContext.database = relatedDatabase(treeItem)
                            2 -> runSelect()
                        }
                    }
                    treeItem.isExpanded = !treeItem.isExpanded
                }
            }
        }
    }

    private fun TreeCell<ExplorerItem>.runSelect() {
        val obj = item.reference
        if (obj is Table) {
            val databaseName = relatedDatabase(treeItem).name
            consoleController.runQuery(databaseName, SELECT_TABLE_TEMPLATE.format(obj.name))
        }
    }

    private fun ddlQueryForItem(kClass: KClass<out Any>): List<DdlOperationType> {
        return when (kClass) {
            Table::class -> listOf(
                DdlOperationType.CREATE_COLUMN,
                DdlOperationType.CREATE_INDEX,
                DdlOperationType.DROP_COLUMN,
                DdlOperationType.DROP_INDEX
            )

            Column::class -> listOf(
                DdlOperationType.CHANGE_COLUMN_NAME,
                DdlOperationType.DROP_COLUMN
            )

            Schema::class -> listOf(
                DdlOperationType.CREATE_TABLE
            )

            else -> listOf()
        }
    }

    private fun relatedDatabase(treeItem: TreeItem<ExplorerItem>): Database {
        var item = treeItem
        while (item.parent != null) {
            if (item.value.reference is Database) {
                break
            }
            item = item.parent
        }
        return item.value.reference as Database
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