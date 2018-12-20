package gui;

import input.FileLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuScene extends Application {
    private FileLoader io;
    private  Stage  window;
    private TextArea pathArea;
    private String fontColor = "-fx-font: 18 arial; -fx-base: #c0c0c0;";
    private String backgroundColor = " -fx-control-inner-background:#B3B3B3; ";
    private String fontDialog = "java.awt.Font.DIALOG";
    private String textFillColor = "#730029";
    private String buttonStyle = "-fx-font: 18 arial; -fx-base: #730029 ;";
    private  String path;
    private String object;
    int space = 30;

    @Override
    public void start(Stage stage){
        /*create window, border and VBoxes displayed on the border*/
        window = new Stage();
        BorderPane border = new BorderPane();
        String borderColor = "-fx-background-color: #c0c0c0;";
        VBox firstBox =  createFirstBox();
        VBox secondBox =  createSecondBox();
        /*set border parameters*/
        border.setStyle(borderColor);
        border.setPrefSize(600,300);
        border.setLeft(firstBox);
        border.setRight(secondBox);
        border.setMargin(firstBox, new Insets(40,0,0,40));
        border.setMargin(secondBox, new Insets(40,40,0,0));
        Scene scene = new Scene(border);
        /*show window until closing*/
        window.setScene(scene);
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        window.showAndWait();
    }

    private VBox createFirstBox(){
        int pathMaxHeight = 10;
        int pathMaxWidth = 240;
        /*create text and area for writing path
         create checkbox to choose the way of download the movie */
        VBox hBox = new VBox();
        hBox.setSpacing(space);
        Text text = new Text("Wpisz ścieżkę do filmu");
        pathArea = new TextArea();
        CheckBox checkBox = new CheckBox("Obraz z kamerki");
        text.setStyle(fontColor);
        text.setFill(Color.web(textFillColor));
        pathArea.setFont(Font.font(fontDialog));
        pathArea.setMaxHeight(pathMaxHeight);
        pathArea.setMaxWidth(pathMaxWidth);
        pathArea.setStyle(backgroundColor);
        checkBox.setStyle(fontColor);

        hBox.getChildren().addAll(text,pathArea,checkBox);
        return hBox;
    }

    public VBox createSecondBox(){
        int buttonMaxWidth = 200;
        int chocieBoxMinWidth = 240;
        /*create text, choiceBox to select the object to be traced and
         button to run the application */
        VBox vBox = new VBox();
        vBox.setSpacing(space);
        Text information = new Text("Wybierz śledzony obiekt");
        ChoiceBox choiceBots=new ChoiceBox(FXCollections.observableArrayList(listOfObjects()));
        Button button= new Button("START");
        choiceBots.setTooltip(new Tooltip("Śledzony obiekt"));
        choiceBots.setValue(listOfObjects().get(0));
        information.setStyle(fontColor);
        information.setFill(Color.web(textFillColor));
        choiceBots.setStyle(fontColor);
        choiceBots.setMinWidth(chocieBoxMinWidth);
        button.setStyle(buttonStyle);
        button.setMaxWidth(buttonMaxWidth);
        /*run the application with the given data and close the window*/
        button.setOnAction(actionEvent -> {
            setPath(pathArea.getText());
            setObject(choiceBots.getValue().toString());
            window.close();
        });

        vBox.getChildren().addAll(information,choiceBots,button);
        return vBox;
    }

    private void setPath(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    private void setObject(String object){
        this.object = object;
    }

    String getObject(){
        return object;
    }
    /*create ArrayList from enum Objects*/
    private ArrayList listOfObjects(){
        ArrayList<Objects> arrayList = new ArrayList<>(Arrays.asList(Objects.values()));
        return arrayList;
    }

}
