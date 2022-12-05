package ru.nsu.dbb.app

import com.google.inject.Guice
import javafx.application.Application
import javafx.stage.Stage

class DbbApplication : Application() {

    private val injector = Guice.createInjector()

    override fun init() {
        injector.injectMembers(this)
    }

    override fun start(primaryStage: Stage?) {

    }
}