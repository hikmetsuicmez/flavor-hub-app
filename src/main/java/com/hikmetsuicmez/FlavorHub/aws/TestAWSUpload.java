//package com.hikmetsuicmez.FoodApp.aws;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URL;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/upload")
//public class TestAWSUpload {
//
//    private final AWSS3Service awsS3Service;
//
//    @PostMapping
//    public ResponseEntity<String> upload(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("keyName") String keyName
//            ) {
//        URL savedFile = awsS3Service.uploadFile(keyName, file);
//        return ResponseEntity.ok("SAVED SUCCESSFULLY: " + savedFile.toString());
//    }
//}
