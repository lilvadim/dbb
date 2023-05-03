package ru.nsu.dbb.config;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.driver.JdbcDrivers;
import ru.nsu.dbb.entity.ConsoleLog;
import ru.nsu.dbb.entity.DatabaseStorage;
import ru.nsu.dbb.response.ExplainPlanResultPipe;
import ru.nsu.dbb.view.AlertFactory;
import ru.nsu.dbb.view.MainController;

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
}
