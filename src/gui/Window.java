package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application implements IWindow {
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        MenuScene menuScene = new MenuScene(stage);
        setScene(menuScene.createScene());
        stage.show();
    }

    @Override
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }



}