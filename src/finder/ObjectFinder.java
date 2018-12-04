package finder;

import analize.SceneAnalizer;
import input.LimitedQueue;
import input.VideoLoader;
import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;


public class ObjectFinder {

    VideoLoader io;
    List<Integer> points_yolo;

    private LimitedQueue<Mat> queue2;
    private int limit = 100;
    private int delay = 100;

    int l, r, b, t;

    public ObjectFinder(VideoLoader io){
        this.io = io;
        queue2 = new LimitedQueue<>(limit);
    }

    //generate random top, bottom, left, right coordinates
    private void rand_four_coordinates(List<Integer> list, int l, int r, int b, int t) {
        l = (int)Math.random()*100;
        r = l + (int)Math.random()*100;
        b = (int)Math.random()*100;
        t = b + (int)Math.random()*100;
    }


    private void calculations() {
        Image img = io.get();
        points_yolo = new ArrayList<Integer>();

        // wykrywanie obiektu na img ...
        // zastępcza
        rand_four_coordinates(points_yolo, l, r, b, t);

        SceneAnalizer calculation = new SceneAnalizer(l, r, b, t);


        int fx = calculation.getFromX();
        int fy = calculation.getFromY();
        int tx = calculation.getToX();
        int ty = calculation.getToY();
        double d = calculation.getDistance();

        ResultVector vector = new ResultVector();
        vector.set(fx, fy, tx, ty, d);

        //try catch because of size - later
        //queue2.add(img2mat);
    }

}
