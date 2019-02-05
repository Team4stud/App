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
3. `New Project Library`->`Java`->`opencv/build/bin/opencv-400.jar `
4. `Add`->`opencv/lib/`

###### Possible errors
- `OpenJDK 64-Bit Server VM warning: You have loaded library /home/mateusz/opencv/build/lib/libopencv_java400.so which might have disabled stack guard. The VM will try to fix the stack guard now.It's highly recommended that you fix the library with 'execstack -c <libfile>', or link it with '-z noexecstack'.`
    1. `execstack --version`
    2. (if not installed) `sudo apt install execstack`
    3. `sudo execstack -c opencv/build/lib/libopencv_java400.so`
    
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

###### TODO
- [ ] zmienić typ pola w klasie Frame z Rectangle2D na Rect (org.opencv.core.Rect) - z tego co widzę yolo zwraca odrazu ten typ więc nie trzeba będzie robić żadnych konwersji, możliwe że trzeba wtedy lekko przerobić Analizer
- [ ] w klasie Frame dodać metodę drawBounds która poprostu nakłada zapisany prostokąt na obrazek i go zwraca, w obiekcie zostaje niezmieniona klatka
- [ ] przenieść Classifier do oddzielnego pakietu/folderu
- [ ] w Classifier.processFrame usunąć rysowanie prostokątów, obsłużyć sytuację jeżeli znajdzie kilka obiektów (np. wybrać ten o największej pewności)
- [ ] sprawdzić obsługę przypadków typu: nie wykryto żadnego obiektu, nie wczytano klatki z dysku itp.
- [ ] znaleźć przykładowy filmik na którym będzie tylko 1 szukany obiekt -> np. 1 osoba
- [ ] dodanie jakiejś walidacji wybranego pliku, przypadek gdy użytkownik wybierze plik który nie jest plikiem video
- [ ] dodać wywołania yolo i analizera do controllera (dopiero jak będzie wszystko gotowe z anallizerem, frame i classifier)
- [ ] (dopiero jak będą gotowe pozostałe moduły) wyświetlanie informacji pobranych z klatki -> usunięcie losowych prostokątów i prawo/lewo/góra/dół zastąpić wartościami x, y
>>>>>>> 4c5afd9bf0955f59879b194d8f53fb45f1ceeff3
