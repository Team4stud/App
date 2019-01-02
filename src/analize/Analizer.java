package analize;

import com.sun.javafx.geom.Point2D;
import frame.Frame;


public class Analizer {
    private Frame prevFrame;
    private Point2D prevPoint;


    public Analizer () {
        this.prevFrame =  null;
    }

    public Frame setDiff(Frame f) {
        if (prevFrame == null) {
            f.diff = new Point2D(0, 0);
            prevFrame = f;
            prevPoint = f.diff;
        } else {
            Point2D center = f.getBoundsCenter();
            f.diff.x = prevPoint.x - center.x;
            f.diff.y = prevPoint.y - center.y;
            prevFrame = f;
            prevPoint = center;
        }

        return f;
    }
}
