package input;

import frame.Frame;
import org.opencv.core.Core;

abstract public class VideoProvider implements Runnable {
    protected String path;

    public VideoProvider(String path){
        this.path = path;
    }

    abstract public Frame getFrame();

    protected void loadOpenCv() {
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")) {
            System.load("D:\\ProgramFiles\\opencv\\opencv\\build\\java\\x64\\opencv_java344.dll");
        } else {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }
}
