package ru.nsu.dbb.view

import javafx.collections.MapChangeListener
import javafx.fxml.FXML
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import ru.nsu.dbb.entity.DatabaseStorage
import ru.nucodelabs.kfx.core.AbstractController
import java.net.URL
import java.util.*
import javax.inject.Inject

class DatabaseExplorerViewController @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val parser: DatabaseStorageToTreeParser
) : AbstractController() {
    @FXML
    private lateinit var treeView: TreeView<String>

    @FXML
    private lateinit var root: VBox
    override val stage: Stage?
        get() = root.scene?.window as Stage?

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        databaseStorage.storage.addListener(MapChangeListener {
            treeView.root = parser.parse(databaseStorage)
        })
    }
}