package flow;

import analize.Analizer;
import frame.Frame;
import input.FileLoader;
import input.VideoProvider;
import org.opencv.imgcodecs.Imgcodecs;
import yolo.Classifier;

public class Controller{
    public VideoProvider video;
    public Analizer analizer;
    public Classifier classifier;
    private int i = 0;

    public Controller(VideoProvider video, Analizer analizer, Classifier classifier) {
        this.video = video;
        this.analizer = analizer;
        this.classifier = classifier;
    }

    public Frame get() {
        Frame frame = video.getFrame();
        if(frame.getFrame().isPresent())
            frame = classifier.processFrame(frame);
        frame.display().ifPresent(img -> Imgcodecs.imwrite("aa/" + i +".jpg", img));
        i++;
//        if(frame.getBounds().isPresent()) frame = analizer.setDiff(frame);

        return frame;
    }


}
