package ru.nsu.dbb.view

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nucodelabs.kfx.core.AbstractViewController
import ru.nucodelabs.kfx.ext.getValue
import ru.nucodelabs.kfx.ext.setValue
import java.io.File
import java.net.URL
import java.util.*
import javax.inject.Inject

class CreateDatabaseScreenController @Inject constructor(
    private val databaseConnectivityController: DatabaseConnectivityController,
    private val alertFactory: AlertFactory
) : AbstractViewController<VBox>() {
    @FXML
    private lateinit var createButton: Button

    @FXML
    private lateinit var fileNameLabel: Label

    private val _chosenFileProperty: ObjectProperty<File> = SimpleObjectProperty()
    private var chosenFile: File? by _chosenFileProperty

    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        _chosenFileProperty.addListener { _, _, newValue ->
            createButton.isDisable = (newValue == null)
        }
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
    }
}

