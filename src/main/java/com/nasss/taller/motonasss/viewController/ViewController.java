package com.nasss.taller.motonasss.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewController {

    // Carga un FXML y devuelve root + controller
    public static <T> ViewLoaderResult<T> loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewController.class.getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();
            return new ViewLoaderResult<>(root, controller);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Clase interna para encapsular root + controller
    public static class ViewLoaderResult<T> {
        private final Parent root;
        private final T controller;

        public ViewLoaderResult(Parent root, T controller) {
            this.root = root;
            this.controller = controller;
        }

        public Parent getRoot() {
            return root;
        }

        public T getController() {
            return controller;
        }
    }
}
