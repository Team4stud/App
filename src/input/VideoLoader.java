package input;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VideoLoader implements Runnable {
    private String path;
    private double height = 0;
    private double width = 0;
    private double fps = 0;

    private LimitedQueue<Mat> queue;
    private int limit = 100;
    private int delay = 100;

    public VideoLoader(String file_path) {
        //System.load("D:\\ProgramFiles\\opencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.path = file_path;
        queue = new LimitedQueue<>(limit);
    }

    public VideoLoader(String file_path, int size_limit) {
        //System.load("D:\\ProgramFiles\\opencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.path = file_path;
        this.limit = size_limit;
        queue = new LimitedQueue<>(limit);
    }

    @Override
    public void run() {
        VideoCapture capture = new VideoCapture(path);

        //width = capture.get(Videoio.CV_CAP_PROP_FRAME_WIDTH);
        //height = capture.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT);
        fps = capture.get(Videoio.CAP_PROP_FPS);

        Mat frame = new Mat();

        while (capture.read(frame)) {

            while (!queue.add(frame)) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    System.out.println("Failed to sleep input.VideoLoader");
                }
            }
        }
    }

    public Mat get() {
        if(!queue.isEmpty())
            return queue.get();
        else
            return null;
    }

    public double getFps() {
        return fps;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int size() {
        return this.queue.size();
    }
}
