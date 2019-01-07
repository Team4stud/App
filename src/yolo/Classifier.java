package yolo;

import com.sun.javafx.geom.Point2D;
import org.opencv.core.Core;
import org.opencv.core.*;
import org.opencv.dnn.*;
import org.opencv.utils.*;
import org.opencv.imgproc.Imgproc;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import frame.Frame;

public class Classifier {

    Net net;
    static ArrayList<String> classes;
    String objectType;

    public Classifier(String weights, String cfg, String classesFile, String objectType) throws IOException {

        net = Dnn.readNetFromDarknet(cfg, weights);
        classes = new ArrayList<>();
        classes = (ArrayList<String>) Files.lines(Paths.get(classesFile)).collect(Collectors.toList());
        this.objectType = objectType;
    }

    private static List<String> getOutputNames(Net net) {

        List<String> names = new ArrayList<>();
        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();
        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));
        return names;
    }

    public Frame processFrame(Frame img) {

        //Optional<Mat> image = img.getFrame();
        //Mat img = image.get();
        Size sz = new Size(416, 416);
        Mat blob = Dnn.blobFromImage(img.getFrame().get(), 0.00392, sz, new Scalar(0), true, false);
        net.setInput(blob);

        List<Mat> result = new ArrayList<>();
        List<String> outBlobNames = getOutputNames(net);
        net.forward(result, outBlobNames);

        float confThreshold = 0.6f;
        List<Integer> clsIds = new ArrayList<>();
        List<Float> confs = new ArrayList<>();
        List<Rect> rects = new ArrayList<>();
        float tempConf = 0;     //used to get most confident object

        for (int i = 0; i < result.size(); ++i)
        {
            Mat level = result.get(i);
            for (int j = 0; j < level.rows(); ++j)
            {
                Mat row = level.row(j);
                Mat scores = row.colRange(5, level.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float)mm.maxVal;
                if (confidence<tempConf) break;
                tempConf = confidence;
                Point classIdPoint = mm.maxLoc;

                if (confidence > confThreshold)
                {
                    int centerX = (int)(row.get(0,0)[0] * img.getFrame().get().cols());
                    int centerY = (int)(row.get(0,1)[0] * img.getFrame().get().rows());
                    int width   = (int)(row.get(0,2)[0] * img.getFrame().get().cols());
                    int height  = (int)(row.get(0,3)[0] * img.getFrame().get().rows());
                    int left    = centerX - width  / 2;
                    int top     = centerY - height / 2;

                    clsIds.add((int)classIdPoint.x);
                    confs.add(confidence);
                    rects.add(new Rect(left, top, width, height));
                }
            }
        }
        //Leave in lists only the most confident object (last one)
        if(confs.isEmpty()) {
            return img;
        }

        Rect c_rect = rects.get(rects.size() - 1);
        Float c_conf = confs.get(confs.size() - 1);
        int c_cl = clsIds.get(clsIds.size() - 1);
        confs.clear();
        rects.clear();
        clsIds.clear();
        confs.add(c_conf);
        rects.add(c_rect);
        clsIds.add(c_cl);

        // Apply non-maximum suppression procedure.
        float nmsThresh = 0.5f;
        MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
        Rect[] boxesArray = rects.toArray(new Rect[0]);
        MatOfRect boxes = new MatOfRect(boxesArray);
        MatOfInt indices = new MatOfInt();
        Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices);
        //System.out.print(boxes.toList());

        // Pass result boxes:
        int [] ind = indices.toArray();
        for (int i = 0; i < ind.length; ++i)
        {
            int idx = ind[i];
            if(classes.get(clsIds.get(idx)).equalsIgnoreCase(objectType)) {
                 Rect box = boxesArray[idx];
                 //Imgproc.rectangle(img.getFrame().get(), box.tl(), box.br(), new Scalar(0,0,255), 2);
                 img.setBounds(box);
             }
        }

        return img;
    }
}
