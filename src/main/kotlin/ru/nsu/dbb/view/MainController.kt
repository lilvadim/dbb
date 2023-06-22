package ru.nsu.dbb.view

import javafx.scene.layout.VBox
import ru.nsu.dbb.view.context.DatabaseContext
import ru.nucodelabs.kfx.core.AbstractViewController
import java.net.URL
import java.util.*
import javax.inject.Inject

class MainController @Inject constructor(
    private val databaseContext: DatabaseContext
) : AbstractViewController<VBox>() {
    override fun initialize(location: URL, resources: ResourceBundle) {
        super.initialize(location, resources)
        databaseContext.databaseProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                stage?.title = "DBB - ${newValue.name}"
            }
        }
    }
}