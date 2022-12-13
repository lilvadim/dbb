package ru.nsu.dbb.view

import javafx.fxml.FXML
import javafx.stage.Stage
import ru.nucodelabs.kfx.core.AbstractController

class CreateDatabaseScreenController : AbstractController() {
    @FXML
    private lateinit var root: Stage
    override val stage: Stage
        get() = root


}

