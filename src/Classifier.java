import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Classifier  {

    private static float confThreshold = (float)0.5;
    private static float nmsThreshold = (float)0.4;
    int inpWidth = 416;
    int inpHeight = 416;

    private static List<String> getOutputNames(Net net) {

        List<String> names = new ArrayList<>();

        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();

        /*
        for(int i=0; i < outLayers.size(); i++) {
            names.add(layersNames.get(outLayers.get(i) - 1));
        }*/

        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));

        return names;
    }

    private static void postprocess(Mat image, List<Mat> outs) {

        List<Integer> classIds = new ArrayList<>();
        List<Float> confidences = new ArrayList<>();
        List<Rect> boxes = new ArrayList<>();
        System.out.println(outs.size());
        for (int i=0; i<outs.size(); i++) {
            System.out.println(outs.get(i));
            //    Mat data = outs.get(i).clone();

            for (int j=0; j<outs.get(i).rows(); j++){
                //data.cols()+=outs.get(i).cols(); ???????
                Mat scores = outs.get(i).row(j).colRange(5, outs.get(i).cols());
                Core.normalize(scores, scores, 0, 1, Core.NORM_MINMAX, -1, new Mat());
                System.out.println(Core.minMaxLoc(scores).maxVal);
                //Core temp = Core.minMaxLoc(scores);
                //Point classIdPoint = temp.maxLoc;
                //double confidence = temp.maxVal;

                Point classIdPoint = Core.minMaxLoc(scores).maxLoc;
                float confidence = (float)Core.minMaxLoc(scores).maxVal;
//System.out.println(classIdPoint);
//System.out.println(confidence);
            /*    if(confidence > confThreshold) {
                    int centerX = (int)data.get(0, 0)[0]*image.cols();
                    int centerY = (int)data.get(0, 0)[1]*image.rows();
                    int width = (int)data.get(0, 0)[2]*image.cols();
                    int height = (int)data.get(0, 0)[3]*image.rows();
                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    classIds.add((int)classIdPoint.x);
                    confidences.add(confidence);
                    boxes.add(new Rect(left, top, width, height));
                }*/
            }
        }

        /*MatOfRect costam = new MatOfRect();
        costam.fromList(boxes);

        MatOfFloat costam2 = new MatOfFloat();
        costam2.fromList(confidences);

        MatOfInt indices = new MatOfInt();
        Dnn.NMSBoxes(costam, costam2, confThreshold, nmsThreshold, indices);

        for (int i=0; i<indices.size().width; i++) { //?? .height?

            int idx = indices.toList().get(i);
            Rect box = boxes.get(idx);
            //drawPred(classIds.get(idx), confidences.get(idx), box.x, box.y, box.x + box.width, box.y + box.height, image);
        }*/
    }

    public static void drawPred(int classId, float conf, int left, int top, int right, int bottom, Mat image) {

        Imgproc.rectangle(image, new Point(left, top), new Point(right, bottom), new Scalar(255, 178, 50), 3);


    }


    public static void main(String[] args) throws IOException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ArrayList<String> classes = new ArrayList<>();

        String classesFile = "/home/ana/Desktop/ObjectDetection-YOLO/coco.names";
        String modelWeights = "/home/ana/Desktop/ObjectDetection-YOLO/yolov3.weights";
        String modelConfiguration = "/home/ana/Desktop/ObjectDetection-YOLO/yolov3.cfg";

        classes = (ArrayList<String>) Files.lines(Paths.get(classesFile)).collect(Collectors.toList());

        classes.forEach(System.out::println);

        Net net = Dnn.readNetFromDarknet(modelConfiguration, modelWeights);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        net.setPreferableTarget(Dnn.DNN_TARGET_CPU);

        Imgcodecs imageCodecs = new Imgcodecs();
        Mat image = imageCodecs.imread("/home/ana/Desktop/ObjectDetection-YOLO/bird.jpg");

        Mat blob = Dnn.blobFromImage(image, 1/255.0, new Size(416, 416), new Scalar(0, 0, 0), true, false);

        net.setInput(blob);

        List<Mat> outs = new ArrayList<Mat>();
        net.forward(outs, getOutputNames(net));

        //outs.forEach(System.out::println);

        postprocess(image, outs);

    }
}