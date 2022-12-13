package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.scene.layout.VBox
import javafx.stage.Stage
import ru.nucodelabs.kfx.core.AbstractController

class OutputViewController : AbstractController() {
    @FXML
    private lateinit var outputBox: VBox
    override val stage: Stage?
        get() = outputBox.scene?.window as Stage?


}