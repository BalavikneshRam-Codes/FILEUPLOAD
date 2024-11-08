package com.BU.FileUpload.service;


import com.BU.FileUpload.entity.Student;
import com.BU.FileUpload.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudentData(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(int id) {
      Optional<Student> findbyid =  studentRepository.findById(id);
      Student student = findbyid.get();
      return student;
    }

    public List<Student> getAllStudentData() {
        return studentRepository.findAll();
    }
}
