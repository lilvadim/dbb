package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.stage.Stage
import ru.nucodelabs.kfx.core.AbstractController

class MainController : AbstractController() {
    @FXML
    private lateinit var _stage: Stage
    override val stage: Stage
        get() = _stage
}