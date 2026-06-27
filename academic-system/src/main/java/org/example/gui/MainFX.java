package org.example.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        GerenciadorTelas gerenciador = new GerenciadorTelas(primaryStage);
        gerenciador.iniciar();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
