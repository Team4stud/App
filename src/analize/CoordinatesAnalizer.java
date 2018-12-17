package analize;

import java.util.List;
import static java.lang.Math.sqrt;

public class CoordinatesAnalizer {
    List<Integer> pointsPrev;
    List<Integer> points;
    double distance;
    double xCentre;
    double yCentre;

    public CoordinatesAnalizer() {
        this.distance = -1.0;
        xCentre = 0;
        yCentre = 0;
    }

    public void setCoordinates(List<Integer> pointsNew) {
        if(distance == -1.0) {
            this.points = pointsNew;
        }
        else {
            pointsPrev = this.points;
            this.points = pointsNew;
        }
    }

    public double getDistance() {
        int l = this.points.get(1);
        int t = this.points.get(2);
        int r = this.points.get(3);
        int b = this.points.get(4);
        int xc = (l + r) / 2;
        int yc = (t + b) / 2;
        double dx2 = (this.xCentre - xc)*(this.xCentre - xc);
        double dy2 = (this.yCentre - yc)*(this.yCentre - yc);

        this.distance = sqrt(dx2 + dy2);
        return this.distance;
    }

}
