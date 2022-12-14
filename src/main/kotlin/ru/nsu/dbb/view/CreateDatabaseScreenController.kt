package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.stage.FileChooser
import javafx.stage.Stage
import ru.nucodelabs.kfx.core.AbstractController
import java.io.File

class CreateDatabaseScreenController : AbstractController() {
    fun chooseFile() {
        val fileChooser = FileChooser()
        val chosenFile: File? = fileChooser.showOpenDialog(stage)
    }

    @FXML
    private lateinit var root: Stage
    override val stage: Stage
        get() = root


}

