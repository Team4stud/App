import org.opencv.core.Core;
import org.opencv.core.*;
import org.opencv.dnn.*;
import org.opencv.utils.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Classifier {

    Net net;

    public Classifier(String weights, String cfg) {

        net = Dnn.readNetFromDarknet(cfg, weights);

    }

    private static List<String> getOutputNames(Net net) {

        List<String> names = new ArrayList<>();
        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();
        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));
        return names;
    }

    public Mat processImage(String imagePath) {

        Mat image = Imgcodecs.imread(imagePath);

        Size sz = new Size(416, 416);
        Mat blob = Dnn.blobFromImage(image, 0.00392, sz, new Scalar(0), true, false);
        net.setInput(blob);

        List<Mat> result = new ArrayList<>();
        List<String> outBlobNames = getOutputNames(net);
        net.forward(result, outBlobNames);

        float confThreshold = 0.6f;
        List<Integer> clsIds = new ArrayList<>();
        List<Float> confs = new ArrayList<>();
        List<Rect> rects = new ArrayList<>();
        for (int i = 0; i < result.size(); ++i)
        {
            Mat level = result.get(i);
            for (int j = 0; j < level.rows(); ++j)
            {
                Mat row = level.row(j);
                Mat scores = row.colRange(5, level.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float)mm.maxVal;
                Point classIdPoint = mm.maxLoc;

                if (confidence > confThreshold)
                {
                    int centerX = (int)(row.get(0,0)[0] * image.cols());
                    int centerY = (int)(row.get(0,1)[0] * image.rows());
                    int width   = (int)(row.get(0,2)[0] * image.cols());
                    int height  = (int)(row.get(0,3)[0] * image.rows());
                    int left    = centerX - width  / 2;
                    int top     = centerY - height / 2;

                    clsIds.add((int)classIdPoint.x);
                    confs.add(confidence);
                    rects.add(new Rect(left, top, width, height));
                    //System.out.println("left:" + left + ", top:" + top + "width: " + width + "height: " + height);
                }
            }
        }

        // Apply non-maximum suppression procedure.
        float nmsThresh = 0.5f;
        MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
        Rect[] boxesArray = rects.toArray(new Rect[0]);
        MatOfRect boxes = new MatOfRect(boxesArray);
        MatOfInt indices = new MatOfInt();
        Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices);

        // Draw result boxes:
        int [] ind = indices.toArray();
        for (int i = 0; i < ind.length; ++i)
        {
            int idx = ind[i];
            Rect box = boxesArray[idx];
            Imgproc.rectangle(image, box.tl(), box.br(), new Scalar(0,0,255), 2);
            //System.out.println(box);
        }
        System.out.println(image);
      //  Imgcodecs.imwrite("out.png", image);
        return image;
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Classifier example = new Classifier("/home/ana/Desktop/ObjectDetection-YOLO/yolov3.weights", "/home/ana/Desktop/ObjectDetection-YOLO/yolov3.cfg");
        example.processImage("/home/ana/Desktop/ObjectDetection-YOLO/bird.jpg");
        example.processImage("/home/ana/darknet/data/ironman.jpg");
    }

}