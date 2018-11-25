package gui;

import input.VideoLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuScene extends Application {
    Boxes boxes;

    @Override
    public void start(Stage stage){
        Stage window = new Stage();
        BorderPane border = new BorderPane();
        String color = "-fx-background-color: #c0c0c0;";
        boxes = new Boxes(window);
        VBox firstBox = boxes.createFirstBox();
        VBox secondBox = boxes.createSecondBox();

        border.setStyle(color);
        border.setPrefSize(600,300);
        border.setLeft(firstBox);
        border.setRight(secondBox);
        border.setMargin(firstBox, new Insets(40,0,0,40));
        border.setMargin(secondBox, new Insets(40,40,0,0));
        Scene scene = new Scene(border, Color.BLACK);

        window.setScene(scene);
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        window.showAndWait();
    }

    public VideoLoader getIo(){
        return boxes.getIo();
    }
}
