import org.opencv.core.Core;
import org.opencv.core.*;
import org.opencv.dnn.*;
import org.opencv.utils.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import flow.Frame;

public class Classifier {

    Net net;
    static ArrayList<String> classes;

    public Classifier(String weights, String cfg, String classesFile) throws IOException {

        net = Dnn.readNetFromDarknet(cfg, weights);
        classes = new ArrayList<>();
        classes = (ArrayList<String>) Files.lines(Paths.get(classesFile)).collect(Collectors.toList());
    }

    private static List<String> getOutputNames(Net net) {

        List<String> names = new ArrayList<>();
        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();
        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));
        return names;
    }

    public Frame processImage(Frame img) {

        //Mat image = img.frame;

        Size sz = new Size(416, 416);
        Mat blob = Dnn.blobFromImage(img.frame, 0.00392, sz, new Scalar(0), true, false);
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
                    int centerX = (int)(row.get(0,0)[0] * img.frame.cols());
                    int centerY = (int)(row.get(0,1)[0] * img.frame.rows());
                    int width   = (int)(row.get(0,2)[0] * img.frame.cols());
                    int height  = (int)(row.get(0,3)[0] * img.frame.rows());
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
        //System.out.print(boxes.toList());

        // Draw result boxes:
        int [] ind = indices.toArray();
        for (int i = 0; i < ind.length; ++i)
        {
            int idx = ind[i];
            Rect box = boxesArray[idx];
            Imgproc.rectangle(img.frame, box.tl(), box.br(), new Scalar(0,0,255), 2);
            System.out.println(box.x);
            img.setBounds(new Rectangle2D.Double(box.x, box.y, box.width, box.height));
          //  System.out.println(classes.get(clsIds.get(idx)));
        }

        return img;
    }

 /*   public static void main(String[] args) throws IOException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Classifier example = new Classifier("/home/ana/Desktop/ObjectDetection-YOLO/yolov3.weights", "/home/ana/Desktop/ObjectDetection-YOLO/yolov3.cfg", "/home/ana/Desktop/ObjectDetection-YOLO/coco.names");
        example.processImage("/home/ana/Desktop/ObjectDetection-YOLO/bird.jpg");
        example.processImage("/home/ana/darknet/data/ironman.jpg");
    }
*/
}