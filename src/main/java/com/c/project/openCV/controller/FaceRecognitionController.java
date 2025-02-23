package com.c.project.openCV.controller;

import com.c.project.openCV.service.FaceRecognitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/face")
public class FaceRecognitionController {

    private final FaceRecognitionService faceRecognitionService;

    public FaceRecognitionController(FaceRecognitionService faceRecognitionService) {
        this.faceRecognitionService = faceRecognitionService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> registerFace(@RequestParam("file") MultipartFile file,
//                                               @RequestParam("username") String username) {
//        try {
//            String result = faceRecognitionService.registerFace(file, username);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
    
    @GetMapping("/register/webcam")
    public ResponseEntity<String> registerFaceFromWebcam(@RequestParam String username) {
        return ResponseEntity.ok(faceRecognitionService.registerFaceFromWebcam(username));
    }
}
