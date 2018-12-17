package flow;

import com.sun.javafx.geom.Point2D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Frame {
    public Mat frame;
    public Rectangle2D bounds;
    public Point2D diff;

    public Frame(Mat frame) {
        this.frame = frame;
    }

    public Frame(Mat frame, Rectangle2D bounds) {
        this.frame = frame;
        this.bounds = bounds;
    }

    public void setBounds(Rectangle2D bounds) {
        this.bounds = bounds;
    }


    public Point2D getBoundiesCenter() {
        Point2D center = new Point2D();
        double x = bounds.getWidth() + bounds.getX()/2;
        double y = bounds.getHeight() + bounds.getY()/2;
        center.x = (float)x;
        center.y = (float)y;

        return center;
    }

    public static Image mat2Image(Mat matrix) {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        byte ba[]=mob.toArray();
        try {
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
            return SwingFXUtils.toFXImage(bi,null);
        } catch (IOException e) {
            System.out.println("failed to convert mat to image");
            return null;
        }
    }
}
