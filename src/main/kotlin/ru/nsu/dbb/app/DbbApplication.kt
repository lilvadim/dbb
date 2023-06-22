package ru.nsu.dbb.app

import com.google.inject.Key
import com.google.inject.name.Names
import javafx.stage.Stage
import ru.nsu.dbb.config.AppModule
import ru.nsu.dbb.daemon.RefreshDaemon
import ru.nucodelabs.kfx.core.GuiceApplication

class DbbApplication : GuiceApplication(AppModule()) {

    override fun start(primaryStage: Stage?) {
        guiceInjector.getInstance(
            Key.get(
                Stage::class.java,
                Names.named("MainView")
            )
        ).show()
    }

    override fun stop() {
        RefreshDaemon.thread.interrupt()
    }
}