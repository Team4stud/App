# App

# https://gist.github.com/rxaviers/7360908

###### Java
`1.8.0_192`
###### OpenCV
`4.0.0`

###### These packages are required to install openCV
* cmake
* ant
* ffmpeg
* pkg-config
* libavcodec-dev
* libavformat-dev
* libavdevice-dev

###### Install openCV 4.0.0
1. `git clone https://github.com/opencv/opencv.git`
2. `cd opencv/`
3. `mkdir build`
4. `cd build`
5. `cmake ..`
6. `make -j8`

###### Add openCV to project in Intellij Idea
1. `File`->`Project Structure...`
2. `Libraries`
3. `New Project Library`->`Java`->`opencv-3.4.3/build/bin/opencv-343.jar `
4. `Add`->`opencv-3.4.3/lib/`

###### Possible errors
- `OpenJDK 64-Bit Server VM warning: You have loaded library /home/mateusz/opencv-3.4.3/build/lib/libopencv_java343.so which might have disabled stack guard. The VM will try to fix the stack guard now.It's highly recommended that you fix the library with 'execstack -c <libfile>', or link it with '-z noexecstack'.`
    1. `execstack --version`
    2. (if not installed) `sudo apt install execstack`
    3. `sudo execstack -c opencv-3.4.3/build/lib/libopencv_java343.so`
    
- `Unable to stop the stream: Inappropriate ioctl for device`
    1. Check if cmake output looks like
    ```
        --FFMEG:            YES
        --avcodec:          YES
        --avformat:         YES
        --avutil:           YES
        --swscale:          YES
        --avresample:       YES
    ```
    If not then install missing packages and rebuild. 

###### Download the pre-trained weight file
`wget https://pjreddie.com/media/files/yolov3.weights`
