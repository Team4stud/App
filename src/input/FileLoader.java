package input;

import frame.Frame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;


public class FileLoader extends VideoProvider {
    private double fps = 0;
    private Mat current;

    public FileLoader(String path) {
        super(path);
        //System.load("D:\\ProgramFiles\\opencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        current = new Mat();
        //loadOpenCv();
    }

    @Override
    public void run() {

        VideoCapture capture = new VideoCapture(path);
        fps = capture.get(Videoio.CAP_PROP_FPS);

        while (capture.read(current)) {
            try {
                Thread.sleep(1000/(int)fps);
            } catch (InterruptedException ex) {
                System.out.println("Failed to sleep input.FileLoader");
            }
        }
    }

    public Frame getFrame() {
        return new Frame(current);
    }
}
