package frame;

import com.sun.javafx.geom.Point2D;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import java.util.Optional;


public class Frame {
    private Mat frame;
    private Rect bounds;
    public Point2D diff;


    public Frame(Mat frame) {
        this.frame = frame;
    }

    public Optional<Mat> getFrame() {
        return Optional.ofNullable(frame);
    }

    public Optional<Rect> getBounds() {
        return Optional.ofNullable(bounds);
    }

    public void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

    public Point2D getBoundsCenter() {
        Point2D center = new Point2D();
        double x = bounds.width + bounds.x/2;
        double y = bounds.height + bounds.y/2;
        center.x = (float)x;
        center.y = (float)y;

        return center;
    }
}
