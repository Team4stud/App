package gui;

import input.VideoLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Boxes {
    private VideoLoader io;
    Stage stage;
    String fontColor = "-fx-font: 18 arial; -fx-base: #c0c0c0;";
    String backgroundColor = " -fx-control-inner-background:#B3B3B3; ";
    String path;

    public Boxes(Stage stage){
        this.stage = stage;
    }

    public VBox createFirstBox(){
        VBox hBox = new VBox();
        hBox.setSpacing(30);

        Text text = new Text("Wpisz ścieżkę do filmu");
        text.setStyle(fontColor);
        text.setFill(Color.web("#730029"));
        TextArea pathArea = new TextArea();
        pathArea.setFont(Font.font(java.awt.Font.DIALOG));
        pathArea.setMaxHeight(10);
        pathArea.setMaxWidth(240);
        pathArea.setStyle(backgroundColor);
        CheckBox checkBox = new CheckBox("Obraz z kamerki");
        checkBox.setStyle(fontColor);

        hBox.getChildren().addAll(text,pathArea,checkBox);
        return hBox;
    }

    public VBox createSecondBox(){
        VBox vBox = new VBox();
        vBox.setSpacing(30);

        Text information = new Text("Wybierz śledzony obiekt");
        information.setStyle(fontColor);
        information.setFill(Color.web("#730029"));

        ChoiceBox choiceBots=new ChoiceBox(FXCollections.observableArrayList("Człowiek","Pies","Auto"));
        choiceBots.setValue("Człowiek");
        choiceBots.setOnAction(e ->
        { System.out.println(choiceBots.getValue()); });
        choiceBots.setTooltip(new Tooltip("Śledzony obiekt"));
        choiceBots.setStyle("-fx-font: 18 arial;"+fontColor);
        choiceBots.setMinWidth(240);

        Button button= new Button("START");
        button.setStyle("-fx-font: 18 arial; -fx-base: #730029 ;");
        button.setMaxWidth(200);
        button.setOnAction(actionEvent -> {
            //setPath(pathArea.getText())
            runApplication();
            stage.close();
        });

        vBox.getChildren().addAll(information,choiceBots,button);
        return vBox;
    }

    private void setPath(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public VideoLoader getIo(){
        return io;
    }

    public void runApplication(){
        io = new VideoLoader("video/sample.mp4", 100);
        Thread thread = new Thread(io);
        thread.start();
    }
}
