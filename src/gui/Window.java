package gui;

import frame.FrameUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application implements IWindow {
    Stage stage;

    public static void main(String[] args) {
        FrameUtils.loadOpenCv();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        /*Show main menu*/
        MenuScene menuScene = new MenuScene();
        menuScene.start(primaryStage);
        /*Create second window after click "Start" button
        * take data form menuScene and run the application*/
        ImageViewer imageViewer = new ImageViewer(menuScene.getPath(),menuScene.getObject());
        setScene(imageViewer.createScene());
        stage.show();
        /*show until closing*/
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