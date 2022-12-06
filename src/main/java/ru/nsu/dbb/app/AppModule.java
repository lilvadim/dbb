package ru.nsu.dbb.app;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ru.nsu.dbb.view.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppModule extends AbstractModule {

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
    URL mainViewFXML() {
        return MainController.class.getResource("MainView.fxml");
    }

    @Provides
    @Named("MainView")
    FXMLLoader fxmlLoader(ResourceBundle uiProperties, Injector injector, @Named("MainView") URL url) {
        FXMLLoader fxmlLoader = new FXMLLoader(url, uiProperties);
        fxmlLoader.setControllerFactory(injector::getInstance);
        return fxmlLoader;
    }

    @Provides
    @Named("MainView")
    Stage mainViewStage(@Named("MainView") FXMLLoader loader) throws IOException {
        return loader.load();
    }
}