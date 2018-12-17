package finder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;

public class ResultObj {
    private Mat img;
    private List<Double> points;
    private double distance;

    public ResultObj(Mat img, List<Double> points, double d) {
        this.img = img;
        this.points = points;
        this.distance = d;
    }

    private Image mat2Image(Mat matrix) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        byte ba[] = mob.toArray();
        try {
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
            return SwingFXUtils.toFXImage(bi,null);
        } catch (IOException e) {
            System.out.println("failed to convert mat to image");
            return null;
        }
    }

    public Image getImg() {
        return mat2Image(this.img);
    }

    public List<Double> getPoints() {
        return points;
    }

    public double getDistance() {

        return distance;
    }
}
