package ru.nsu.dbb.view.context

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import ru.nsu.dbb.entity.Database
import ru.nucodelabs.kfx.ext.getValue
import ru.nucodelabs.kfx.ext.setValue

class DatabaseContext(
    database: Database?
) {
    private val databaseProperty = SimpleObjectProperty(database)
    fun databaseProperty(): ObjectProperty<Database?> = databaseProperty
    var database: Database? by databaseProperty
}