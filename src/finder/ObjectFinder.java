package finder;

import analize.CoordinatesAnalizer;
import input.LimitedQueue;
import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;


public class ObjectFinder {

    private List<Double> points_yolo;
    private LimitedQueue<Mat> queue1;
    private CoordinatesAnalizer calculations;

    private LimitedQueue<ResultObj> queue2;
    private int limit = 100;
    private int delay = 100;

    private double d;
    private Mat img;
    private ResultObj obj2;

    public ObjectFinder(LimitedQueue<Mat> queue1){
        this.queue1 = queue1;
        this.queue2 = new LimitedQueue<>(limit);
        this.calculations = new CoordinatesAnalizer();
        points_yolo = new ArrayList<>();
        d = 0;
    }

    //generate random coordinates & add it to list
    private void rand_four_coordinates() {
        double l, r, b, t;
        l = Math.random()*100;
        r = l + Math.random()*100;
        b = Math.random()*100;
        t = b + Math.random()*100;
        this.points_yolo.add(l);
        this.points_yolo.add(t);
        this.points_yolo.add(r);
        this.points_yolo.add(b);
    }

    private void calculate_changes() {
        img = queue1.get();

        // wykrywanie obiektu na img ...
        // zastępcza: generuje 4 koordynaty w Liscie points_yolo
        rand_four_coordinates();

        d = calculations.getDistance();
        obj2 = new ResultObj(img, points_yolo, d);

        //try catch because of size - later
        queue2.add(obj2);
    }
}
