package gui;

import javafx.application.Application;
import javafx.application.Platform;
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
        MenuScene menuScene = new MenuScene();
        menuScene.start(primaryStage);
        ImageViewer imageViewer = new ImageViewer(menuScene.getIo());
        setScene(imageViewer.createScene());
        stage.show();

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

}