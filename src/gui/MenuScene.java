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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuScene extends Application {

    private FileLoader io;
    private Stage  window;
    private TextArea pathArea;
    private String fontColor = "-fx-font: 18 arial; -fx-base: #c0c0c0;";
    private String backgroundColor = " -fx-control-inner-background:#B3B3B3; ";
    private String fontDialog = "java.awt.Font.DIALOG";
    private String textFillColor = "#730029";
    private String buttonStyle = "-fx-font: 18 arial; -fx-base: #730029 ;";
    private  String path;
    private String object;
    int space = 30;
    private CheckBox checkBox;
    private boolean isPointed = false;

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
        border.setPrefSize(600,400);
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
        int buttonMaxWidth = 200;
        /*create text and area for writing path
         create checkbox to choose the way of download the movie */
        VBox hBox = new VBox();
        hBox.setSpacing(space);

        Text text = new Text("Wybierz ścieżkę do filmu");
        Button button= new Button("Przeglądaj pliki");
        button.setStyle(fontColor);
        button.setMaxWidth(buttonMaxWidth);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        checkBox = new CheckBox("Obraz z kamerki");
        text.setStyle(fontColor);
        text.setFill(Color.web(textFillColor));
        checkBox.setStyle(fontColor);
        hBox.getChildren().addAll(text,button,checkBox);

        /*run the application with the given data and close the window*/
        button.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                String path = file.toString();
                setPath(path);
            }
        });
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
        ChoiceBox choiceBots = new ChoiceBox(FXCollections.observableArrayList(listOfObjects()));
        Button button = new Button("START");
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
            isPointed = checkBox.isSelected();
            setObject(choiceBots.getValue().toString());
            window.close();
        });

        vBox.getChildren().addAll(information,choiceBots,button);
        return vBox;
    }

    private void setPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

    private void setObject(String object)
    {
        this.object = object;
    }
    String getObject()
    {
        return this.object;
    }

    boolean isCheckboxPointed (){
        return isPointed;
    }

    /*create ArrayList from enum Objects*/
    private ArrayList listOfObjects(){
        File file = new File("/home/katarzyna/zespolowe/coco.names");
        BufferedReader br = null;
        ArrayList<String> arrayList = new ArrayList<>();
       // ArrayList<Objects> arrayList = new ArrayList<>(Arrays.asList(Objects.values()));
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
               arrayList.add(st);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
