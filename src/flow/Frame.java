package flow;

import com.sun.javafx.geom.Rectangle;
import org.opencv.core.Mat;

import java.awt.*;

public class Frame {
    private Mat frame;
    private Rectangle bounds;
    private Point diff;

    public Frame(Mat frame) {
        this.frame = frame;
    }

    public Frame(Mat frame, Rectangle bounds) {
        this.frame = frame;
        this.bounds = bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setDiff(Point diff) {
        this.diff = diff;
    }

    public Point getBoundiesCenter() {
        return new Point(1, 1);
    }
}
