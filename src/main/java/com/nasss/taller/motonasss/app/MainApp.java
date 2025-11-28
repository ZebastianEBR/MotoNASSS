package com.nasss.taller.motonasss.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/nasss/taller/motonasss/fxml/main_view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720); // tamaño recomendado
        stage.setScene(scene);
        stage.setTitle("MotoNASSS - Sistema de Ventas");
        stage.setMinWidth(1024);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}