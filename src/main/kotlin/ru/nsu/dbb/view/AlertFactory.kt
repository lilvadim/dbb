package ru.nsu.dbb.view

import javafx.scene.control.Alert
import javafx.stage.Stage

class AlertFactory {
    @JvmOverloads
    fun simpleExceptionAlert(e: Throwable, owner: Stage? = null): Alert =
        Alert(Alert.AlertType.ERROR, e.message).apply {
            title = "Something went wrong"
            headerText = title
            initOwner(owner)
        }
}