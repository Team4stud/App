package finder;

public class ResultVector {
    int fromx;
    int fromy;
    int tox;
    int toy;
    double distance;

    public ResultVector() {}


    public void set (int fx, int fy, int tx, int ty, double d) {
        this.fromx = fx;
        this.fromy = fy;
        this.tox = tx;
        this.toy = ty;
        this.distance = d;
    }
}
