import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class HelloCV {

    /* test if openCV works, saves 50 frames
        args[0] - path to mp4 file
        args[1] - output directory - must already exists
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture capture = new VideoCapture(args[0]);
        System.out.println(Videoio.CV_CAP_PROP_FRAME_HEIGHT);

        Mat frame = new Mat();

        int count = 0;

        while (capture.read(frame) && count < 50) {

            Imgcodecs.imwrite(args[1]+"/"+count+".png", frame);
            System.out.println(args[1]+"/"+count+".png");
            count++;
        }

        capture.release();
    }

}
