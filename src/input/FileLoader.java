package input;

import frame.Frame;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


public class FileLoader extends VideoProvider {
    private String path = "";

    public FileLoader(String path) {
        current = new Mat();
        fps = 1.0;
        this.path = path;
    }

    @Override
    protected VideoCapture getVideoCapture() {
        return new VideoCapture(path);
    }

    @Override
    public Frame getFrame() {
        if(current.empty()) {
            return new Frame(null);
        } else {
            return new Frame(current);
        }
    }
}
