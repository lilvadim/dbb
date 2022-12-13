package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.scene.layout.VBox
import javafx.stage.Stage
import ru.nucodelabs.kfx.core.AbstractController

class DatabaseExplorerViewController : AbstractController() {
    @FXML
    private lateinit var root: VBox
    override val stage: Stage?
        get() = root.scene?.window as Stage?

}