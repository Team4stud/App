package flow;

import input.VideoLoader;
import org.opencv.core.Mat;

public class Controller implements Runnable {
    public String path;
    public String object;
    public String cfg;
    public String weigts;

    public VideoLoader io;
    public OutputQueue out;
    public Analizer  analizer;

    public Controller(String path, String object) {
        this.path = path;
        this.object = object;

        io = new VideoLoader(path);
        out = new OutputQueue();
        analizer = new Analizer();

        Thread thread_io = new Thread(io);
        thread_io.start();


    }

    @Override
    public void run() {


        while (true) {
            Mat mat = io.get();
            if(mat != null) {
                Frame frame = new Frame(io.get());
                //frame = analizer.setDiff(frame);
                out.add(frame);
            }
            //yolo
            //analizer
            //
        }
    }

    public Frame get() {
        return new Frame(io.get());
    }
}
