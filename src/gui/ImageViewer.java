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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageViewer {
    BorderPane border;
    Stage stage;
    Scene scene;
    VideoLoader io;

    public ImageViewer(BorderPane border, Scene scene, Stage stage){
        this.border=border;
        this.scene=scene;
        this.stage=stage;
    }

    public void showPhotos(){
        ImageView imageView = new ImageView();
        imageView.setCursor(Cursor.CLOSED_HAND);
        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Pane stackPane = new Pane();

        Timeline timeline  = new Timeline();
        timeline.setCycleCount(
                Animation.INDEFINITE
        );

        EventHandler<ActionEvent>
                onFishined = arg0 -> {
                try {
                    imageView.setImage(io.get());
                    stackPane.getChildren().setAll(imageView,setRectangle());

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

        border.setBottom(hbox);
        border.setMargin(hbox, new Insets(0,50,30,50));
        stage.show();
    }


    public void runApplication(){
        io = new VideoLoader("video/sample.mp4", 100);
        Thread thread = new Thread(io);
        thread.start();
    }

    private Rectangle setRectangle(){
        Rectangle rectangle = new Rectangle(Math.random()*100,Math.random()*100,
                    Math.random()*100,Math.random()*100);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        return rectangle;
    }

}
