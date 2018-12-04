package analize;

import finder.ResultVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class SceneAnalizer {
    int l;
    int r;
    int b;
    int t;


    public SceneAnalizer(int l, int r, int b, int t) {
        this.l = l;
        this.r = r;
        this.b = b;
        this.t = t;
    }

    public int getFromX() {

        return 0;
    }
    public int getFromY() {

        return 0;
    }

    public int getToX() {
        int x = (r + l)/2;
        return x;
    }

    public int getToY() {
        int y = (t + b)/2;
        return y;
    }

    public double getDistance() {
        int dx2 = (getFromX() - getToX())*(getFromX() - getToX());
        int dy2 = (getFromY() - getToY())*(getFromY() - getToY());
        double d = sqrt(dx2 +dy2);
        return d;
    }

}
