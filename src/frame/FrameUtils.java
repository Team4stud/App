package frame;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FrameUtils {

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
