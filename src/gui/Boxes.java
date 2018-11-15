package gui;

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
    Stage stage;
    Photos photos;

    public Boxes(Stage stage, Photos photo){
        this.stage = stage;
        this.photos = photo;
    }

    public VBox createFirstBox(){
        VBox hBox = new VBox();
        hBox.setSpacing(20);

        Text text = new Text("Wpisz ścieżkę do filmu");
        text.setStyle("-fx-font: 18 arial; -fx-base: #c0c0c0;");
        text.setFill(Color.web("#730029"));

        TextArea pathArea = new TextArea();
        pathArea.setFont(Font.font(java.awt.Font.DIALOG));
        pathArea.setMaxHeight(10);
        pathArea.setMaxWidth(240);
        pathArea.setStyle(" -fx-control-inner-background:#B3B3B3; ");

        CheckBox checkBox = new CheckBox("Obraz z kamerki");
        checkBox.setStyle("-fx-font: 18 arial; -fx-base: #c0c0c0;");

        hBox.getChildren().addAll(text,pathArea,checkBox);

        return hBox;
    }

    public VBox createSecondBox(){
        VBox vBox = new VBox();
        vBox.setSpacing(20);

        Text information = new Text("Wybierz śledzony obiekt");
        information.setStyle("-fx-font: 18 arial; -fx-base: #c0c0c0;");
        information.setFill(Color.web("#730029"));

        ChoiceBox choiceBots=new ChoiceBox(FXCollections.observableArrayList("Człowiek","Pies","Auto"));
        choiceBots.setValue("Człowiek");
        choiceBots.setOnAction(e ->
        { System.out.println(choiceBots.getValue()); });
        choiceBots.setTooltip(new Tooltip("Śledzony obiekt"));
        choiceBots.setStyle("-fx-font: 18 arial; -fx-base: #c0c0c0;");
        choiceBots.setMinWidth(240);

        Button button= new Button("START");
        button.setStyle("-fx-font: 18 arial; -fx-base: #730029 ;");
        button.setMaxWidth(200);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //final String path = pathArea.getText();
                stage.setTitle("Film");
                photos.runApplication();
                photos.showPhotos();
            }
        });
        vBox.getChildren().addAll(information,choiceBots,button);

        return vBox;
    }

    public VBox createInformationBox(){
        VBox vBox = new VBox();
        vBox.setSpacing(20);

        TextArea pathArea = new TextArea();
        pathArea.setFont(Font.font(java.awt.Font.DIALOG));
        pathArea.setMaxHeight(10);
        pathArea.setMaxWidth(200);
        pathArea.setStyle(" -fx-control-inner-background:#B3B3B3; ");

        vBox.getChildren().addAll(pathArea);
        return vBox;
    }

}
