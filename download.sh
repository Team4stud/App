#/bin/bash

FILES=(https://raw.githubusercontent.com/pjreddie/darknet/master/cfg/yolov3.cfg https://raw.githubusercontent.com/opencv/opencv/master/samples/data/dnn/object_detection_classes_yolov3.txt https://pjreddie.com/media/files/yolov3.weights)

for FILE in "${FILES[@]}" ; do
	wget "$FILE"
done
