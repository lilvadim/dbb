package ru.nsu.dbb.view

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.stage.FileChooser
import javafx.stage.Stage
import ru.nsu.dbb.controller.DatabaseConnectivityController
import ru.nucodelabs.kfx.core.AbstractController
import ru.nucodelabs.kfx.ext.getValue
import ru.nucodelabs.kfx.ext.setValue
import java.io.File
import javax.inject.Inject

class CreateDatabaseScreenController @Inject constructor(
    private val databaseConnectivityController: DatabaseConnectivityController,
    private val alertFactory: AlertFactory
) : AbstractController() {
    @FXML
    private lateinit var createButton: Button

    @FXML
    private lateinit var fileNameLabel: Label

    @FXML
    private lateinit var root: Stage
    override val stage: Stage
        get() = root

    private val _chosenFileProperty: ObjectProperty<File> = SimpleObjectProperty()
    private var chosenFile: File? by _chosenFileProperty

    init {
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
            stage.hide()
        } catch (e: Exception) {
            alertFactory.simpleExceptionAlert(e, stage).show()
        }
    }
}

