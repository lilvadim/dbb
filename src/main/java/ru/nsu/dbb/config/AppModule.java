package ru.nsu.dbb.config;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ru.nsu.dbb.config.driver.JdbcDrivers;
import ru.nsu.dbb.controller.ConsoleController;
import ru.nsu.dbb.controller.DatabaseConnectivityController;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.ConsoleLog;
import ru.nsu.dbb.entity.DatabaseStorage;
import ru.nsu.dbb.response.ExplainPlanResultPipe;
import ru.nsu.dbb.response.SelectResultPipe;
import ru.nsu.dbb.view.AlertFactory;
import ru.nsu.dbb.view.MainController;
import ru.nsu.dbb.view.context.DatabaseContext;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        bind(DatabaseStorage.class).in(Singleton.class);
        bind(ConsoleLog.class).in(Singleton.class);
        bind(AlertFactory.class).in(Singleton.class);
        bind(Driver.class).in(Singleton.class);
        bind(ExplainPlanResultPipe.class).in(Singleton.class);
        bind(SelectResultPipe.class).in(Singleton.class);
        bind(DatabaseConnectivityController.class).in(Singleton.class);
        bind(ConsoleController.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    ResourceBundle uiProperties() {
        return ResourceBundle.getBundle("ru/nsu/dbb/view/UI", new Locale("en"));
    }

    @Provides
    @Named("CSS")
    @Singleton
    String stylesheet() {
        return "ru/nsu/dbb/view/common.css";
    }

    @Provides
    @Named("MainView")
    Stage mainViewStage(Injector injector, ResourceBundle uiProperties) throws IOException {
        URL url = MainController.class.getResource("MainView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url, uiProperties);
        fxmlLoader.setControllerFactory(injector::getInstance);
        return fxmlLoader.load();
    }

    @Provides
    @Singleton
    JdbcDrivers.DriverList driverList() {
        return JdbcDrivers.getDriverList();
    }

    @Provides
    @Singleton
    DatabaseContext databaseContext() {
        return new DatabaseContext(null);
    }
}
