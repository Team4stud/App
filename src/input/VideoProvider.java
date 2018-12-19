package input;

import frame.Frame;

abstract public class VideoProvider implements Runnable {
    protected String path;

    public VideoProvider(String path){
        this.path = path;
    }

    abstract public Frame getFrame();
}
