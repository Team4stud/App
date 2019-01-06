package frame;

import com.sun.javafx.geom.Point2D;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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
        double x = bounds.width + bounds.x/2.0;
        double y = bounds.height + bounds.y/2.0;
        center.x = (float)x;
        center.y = (float)y;

        return center;
    }

    public Optional<Mat> display() {
        Mat img = frame;

        if(getBounds().isPresent())
            Imgproc.rectangle(img, bounds, new Scalar(0.0, 0.0, 1.0), 3);

        return Optional.ofNullable(img);
    }
}
