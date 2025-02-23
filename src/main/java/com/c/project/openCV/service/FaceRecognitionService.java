package com.c.project.openCV.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class FaceRecognitionService {

    private static final String FACE_CASCADE_PATH = "src/main/resources/haarcascade_frontalface_default.xml";
    private static final String FACE_STORAGE_PATH = "/openCV/src/main/resources/faces/";

    static {
        System.loadLibrary("opencv_java480"); // Load OpenCV only once
    }

    public String registerFaceFromWebcam(String username) {
        // Open the default webcam (index 0)
        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            return "Error: Could not open webcam!";
        }

        // Load face detector
        CascadeClassifier faceDetector = new CascadeClassifier(FACE_CASCADE_PATH);
        if (faceDetector.empty()) {
            return "Error: Could not load face cascade!";
        }

        Mat frame = new Mat();
        boolean faceCaptured = false;

        while (!faceCaptured) {
            camera.read(frame);

            if (frame.empty()) {
                return "Error: Could not capture image!";
            }

            // Resize frame for better processing
            Imgproc.resize(frame, frame, new Size(500, 500));

            // Detect faces
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(frame, faceDetections);

            if (!faceDetections.empty()) {
                for (Rect face : faceDetections.toArray()) {
                    Mat faceImage = new Mat(frame, face);
                    String faceFileName = Paths.get(FACE_STORAGE_PATH, username + ".jpg").toString();
                    Imgcodecs.imwrite(faceFileName, faceImage);
                    faceCaptured = true;
                    break;
                }
            }
        }

        camera.release(); // Release the webcam
        return "Face registered successfully from webcam!";
    }
}
