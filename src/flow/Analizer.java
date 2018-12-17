package flow;

import analize.CoordinatesAnalizer;
import com.sun.javafx.geom.Point2D;
import com.sun.javafx.geom.Rectangle;
import input.LimitedQueue;
import org.opencv.core.Mat;

import java.awt.*;

public class Analizer {
    private Frame prevFrame;
    private Point2D prevPoint;

    public Analizer () {
        this.prevFrame =  null;
    }


    public Frame setDiff(Frame f) {

        if (prevFrame == null) {
            f.diff = new Point2D();
            prevFrame = f;
            prevPoint = f.diff;
        }
        else {
            Point2D center = f.getBoundiesCenter();
            f.diff.x = prevPoint.x - center.x;
            f.diff.y = prevPoint.y - center.y;
            prevFrame = f;
            prevPoint = center;
        }
        return f;
    }


}
