package ru.nsu.dbb.daemon

import javafx.application.Platform
import kotlin.concurrent.thread

object RefreshDaemon {
    @JvmStatic
    var callback: Runnable = Runnable {}

    @JvmStatic
    val thread = thread(start = true) {
        while (true) {
            try {
                Thread.sleep(10000)
            } catch (e: InterruptedException) {
                break
            }
            Platform.runLater { callback.run() }
            println("Database refreshed")
        }
    }
}