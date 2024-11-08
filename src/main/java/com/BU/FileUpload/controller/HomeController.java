package com.BU.FileUpload.controller;


import com.BU.FileUpload.entity.Student;
import com.BU.FileUpload.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private StudentService studentService;
    private static String uploadDirectory = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "images").toString();
    @PostMapping("/saveData")
    public Student saveStudent(@ModelAttribute Student student, @RequestParam("file")MultipartFile multipartFile) throws IOException {
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory and any non-existent parent directories
        }

        String originalFileName = multipartFile.getOriginalFilename();
        Path filenameAndPath=Paths.get(uploadDirectory,originalFileName);
        System.out.println("Saving file to: " + filenameAndPath.toString());
        Files.write(filenameAndPath,multipartFile.getBytes());
        student.setProfileImage(originalFileName);
        Student saveStudentData = studentService.saveStudentData(student);
        return saveStudentData;
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
       Student student =  studentService.getStudentById(id);
       return ResponseEntity.ok().body(student);
    }

    @GetMapping("/student/profile/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable int id) throws IOException {
        Student student = studentService.getStudentById(id);
        Path imagePath = Paths.get(uploadDirectory,student.getProfileImage());
        Resource resource = new FileSystemResource(imagePath.toFile());
        String contentType = Files.probeContentType(imagePath);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
    @GetMapping("/students")
    public List<Student> getAllStudentData(){
        return studentService.getAllStudentData();
    }
}
