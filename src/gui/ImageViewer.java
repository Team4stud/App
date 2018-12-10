package gui;

import input.VideoLoader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class ImageViewer {

    VideoLoader io;

    public ImageViewer(VideoLoader io){
        this.io = io;
    }

    public Scene  createScene(){
        ImageView imageView = new ImageView();
        imageView.setCursor(Cursor.CLOSED_HAND);
        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        String color = "-fx-background-color: #c0c0c0;";
        String fontColor = "-fx-font: 18 arial; -fx-base: #c0c0c0;";
        Pane stackPane = new Pane();
        Text text = new Text();
        text.setStyle(fontColor);
        text.setFill(Color.web("#730029"));
        text.setStyle("-fx-font: 24 arial; -fx-base: #730029 ;");

        Timeline timeline  = new Timeline();
        timeline.setCycleCount(
                Animation.INDEFINITE
        );

        EventHandler<ActionEvent>
                onFishined = arg0 -> {
                    try {
                        imageView.setImage(io.get());
                        stackPane.getChildren().setAll(imageView,setRectangle());
                        text.setText(setText());

                    } catch (Exception e) {
                        e.printStackTrace();
                }
            };
        KeyFrame kf = new KeyFrame(Duration.seconds(1*0.08), onFishined,null,null );
        timeline.getKeyFrames().add(kf);
        timeline.play();

        HBox hbox = new HBox();
        hbox.setMinSize(400,400);
        hbox.getChildren().addAll(stackPane);
        BorderPane border = new BorderPane();
        border.setTop(text);
        border.setMargin(text, new Insets(20,50,50,380));
        border.setBottom(hbox);
        border.setStyle(color);
        border.setPrefSize(800,500);
        border.setMargin(hbox, new Insets(0,50,30,50));
        return new Scene(border, Color.BLACK);
    }

    private Rectangle setRectangle(){
        Rectangle rectangle = new Rectangle(Math.random()*100,Math.random()*100,
                    Math.random()*100,Math.random()*100);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        return rectangle;
    }

    private String setText(){
        String[] texts= new String[4];
        texts[0] = "PRAWO";
        texts[1] = "LEWO";
        texts[2] = "GÓRA";
        texts[3] = "DÓŁ";

        return  texts[(int) (Math.random()*1000%4)];
    }

}
