package flow;

import analize.Analizer;
import frame.Frame;
import input.FileLoader;
import input.VideoProvider;

public class Controller{
    public VideoProvider video;
    public Analizer analizer;
    //public Classifier classifier;

    public Controller(VideoProvider video, Analizer analizer) { //Classifier
        this.video = video;
        this.analizer = analizer;
    }

    public Frame get() {
        Frame frame = video.getFrame();
        //classifier
        //analizer
        return frame;
    }
}
