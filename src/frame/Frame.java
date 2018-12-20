package frame;

import com.sun.javafx.geom.Point2D;
import org.opencv.core.Mat;
import java.awt.geom.Rectangle2D;
import java.util.Optional;


public class Frame {
    private Mat frame;
    private Rectangle2D bounds;
    public Point2D diff;


    public Frame(Mat frame) {
        this.frame = frame;
    }

    public Frame(Mat frame, Rectangle2D bounds) {
        this.frame = frame;
        this.bounds = bounds;
    }

    public Optional<Mat> getFrame() {
        return Optional.ofNullable(frame);
    }

    public void setBounds(Rectangle2D bounds) {
        this.bounds = bounds;
    }

    public Point2D getBoundsCenter() {
        Point2D center = new Point2D();
        double x = bounds.getWidth() + bounds.getX()/2;
        double y = bounds.getHeight() + bounds.getY()/2;
        center.x = (float)x;
        center.y = (float)y;

        return center;
    }
}
