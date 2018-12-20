package input;

import frame.Frame;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


public class CameraLoader extends VideoProvider {
    private int deviceId = 0;


    public CameraLoader(int deviceId) {
        current = new Mat();
        fps = 1.0;
        this.deviceId = deviceId;
    }

    @Override
    protected VideoCapture getVideoCapture() {
        return new VideoCapture(deviceId);
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