package ru.nsu.dbb.view

import javafx.beans.binding.Bindings.not
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nsu.dbb.entity.DatabaseStorage
import ru.nucodelabs.kfx.core.AbstractViewController
import ru.nucodelabs.kfx.ext.getValue
import ru.nucodelabs.kfx.ext.isNotBlank
import ru.nucodelabs.kfx.ext.setValue
import java.io.File
import java.net.URL
import java.util.*
import javax.inject.Inject

class CreateDatabaseScreenController @Inject constructor(
    private val databaseConnectivityController: DatabaseConnectivityController,
    private val databaseStorage: DatabaseStorage,
    private val alertFactory: AlertFactory
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var manualUrl: TextField


    @FXML
    private lateinit var createButton: Button

    @FXML
    private lateinit var fileNameLabel: Label

    private val _chosenFileProperty: ObjectProperty<File> = SimpleObjectProperty()
    private var chosenFile: File? by _chosenFileProperty

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        createButton.disableProperty()
            .bind(not(_chosenFileProperty.isNotNull.or(manualUrl.textProperty().isNotBlank())))
    }

    fun chooseFile() {
        val fileChooser = FileChooser()
        val chosenFile: File? = fileChooser.showOpenDialog(stage)

        if (chosenFile != null) {
            this.chosenFile = chosenFile
            fileNameLabel.text = chosenFile.name
        }
    }

    fun create() {
        if (manualUrl.text.isEmpty()) {
            try {
                databaseConnectivityController.createDatabase(
                    chosenFile?.name,
                    "jdbc:sqlite:${chosenFile?.absolutePath}",
                    "",
                    ""
                )
                stage!!.hide()
            } catch (e: Exception) {
                alertFactory.simpleExceptionAlert(e, stage).show()
            }
        } else {
            try {
                databaseConnectivityController.createDatabase(
                    "database#${databaseStorage.storage.keys.size}",
                    manualUrl.text,
                    "",
                    ""
                )
                stage!!.hide()
            } catch (e: Exception) {
                alertFactory.simpleExceptionAlert(e, stage).show()
            }
        }
    }
}

