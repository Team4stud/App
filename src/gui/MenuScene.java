package gui;

import gui.Boxes;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuScene {
    private Stage stage;
    private BorderPane border;
    private Scene scene;
    private Boxes boxes;
    private ImageViewer photos;

    public MenuScene(Stage primaryStage){
        this.stage = primaryStage;
        this.border =new BorderPane();
        this.photos = new ImageViewer(border,scene,stage);
        this.boxes = new Boxes(stage,photos);
    }

    public Scene createScene(){
        String color = "-fx-background-color: #c0c0c0;";
        VBox firstBox = boxes.createFirstBox();
        VBox secondBox = boxes.createSecondBox();
        VBox informationBox = boxes.createInformationBox();

        border.setStyle(color);
        border.setPrefSize(800,700);
        border.setLeft(firstBox);
        border.setRight(secondBox);
        border.setCenter(informationBox);
        border.setMargin(firstBox, new Insets(20,0,0,40));
        border.setMargin(secondBox, new Insets(20,40,0,0));
        border.setMargin(informationBox, new Insets(190,0,0,20));
        scene = new Scene(border, Color.BLACK);

        return scene;
    }
}
