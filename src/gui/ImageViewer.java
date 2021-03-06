package gui;


import analize.Analizer;
import flow.Controller;
import frame.Frame;

import frame.FrameUtils;
import input.CameraLoader;
import input.FileLoader;
import input.VideoProvider;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import yolo.Classifier;

import java.io.IOException;
import java.util.Optional;

public class ImageViewer {
    Controller flow;

    public Scene  createScene(){
        String color = "-fx-background-color: #c0c0c0;";
        String fontColor = "-fx-font: 18 arial; -fx-base: #c0c0c0;";
        String textColor = "#730029";
        String textStyle = "-fx-font: 24 arial; -fx-base: #730029 ;";
        /*create imageView to display photos*/
        ImageView imageView = new ImageView();
        imageView.setCursor(Cursor.CLOSED_HAND);
        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        Pane stackPane = new Pane();
        Text text = new Text();
        text.setStyle(fontColor);
        text.setFill(Color.web(textColor));
        text.setStyle(textStyle);
        /* A Timeline, defined by one or more KeyFrames, processes individual KeyFrame sequentially,
        in the order specified by KeyFrame.time.*/
        Timeline timeline  = new Timeline();
        timeline.setCycleCount(
                Animation.INDEFINITE
        );
        /*display photos and rectangle on them*/
        EventHandler<ActionEvent>
                onFinished = arg0 -> {

            try {
                Frame frame = flow.get();
                //flow.get().getFrame().ifPresent(img -> imageView.setImage(FrameUtils.mat2Image(img)));
                frame.display().ifPresent(value -> imageView.setImage(FrameUtils.mat2Image(value)));
                stackPane.getChildren().setAll(imageView);
                text.setText("X: "+frame.diff.x + " Y: "+frame.diff.y );
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(1000),  onFinished,null,null );
        /*add the keyframe to the timeline*/
        timeline.getKeyFrames().add(kf);
        timeline.play();
        /*Create border to show photos and text*/
        HBox hbox = new HBox();
        hbox.setMinSize(400,400);
        hbox.getChildren().addAll(stackPane);
        BorderPane border = new BorderPane();
        border.setTop(text);
        border.setMargin(text, new Insets(70,50,20,350));
        border.setBottom(hbox);
        border.setStyle(color);
        border.setPrefSize(800,700);
        border.setMargin(hbox, new Insets(0,50,90,50));
        return new Scene(border, Color.BLACK);
    }

    public void runApplication(String path, String objectToFollow, boolean readFromFile){

        VideoProvider video;
        if(!readFromFile) {
            if (path == null)
                System.exit(0);
            video = new FileLoader(path);
        }
        else
            video = new CameraLoader(0);
        Thread input = new Thread(video);
        input.start();

        Classifier classifier = null;
        Analizer analizer = new Analizer();
        try {
            classifier = new Classifier("/home/katarzyna/zespolowe/yolov3.weights",
                    "/home/katarzyna/zespolowe/yolov3.cfg",
                    "/home/katarzyna/zespolowe/coco.names",
                    objectToFollow);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        flow = new Controller(video, analizer, classifier);
    }

}
