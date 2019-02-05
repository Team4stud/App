package input;

import frame.Frame;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public abstract class VideoProvider implements Runnable {
    protected Mat current;
    protected double fps;
    protected abstract VideoCapture getVideoCapture();

    public abstract Frame getFrame();

    public double getFps() {
        return this.fps;
    }

    @Override
    public void run() {
        VideoCapture capture = getVideoCapture();
        fps = capture.get(Videoio.CAP_PROP_FPS);

        while (capture.read(current)) {
            try {
                Thread.sleep(1000/(int)fps);
            } catch (InterruptedException ex) {
                System.out.println("Failed to sleep input.FileLoader");
            }
        }
    }
}
