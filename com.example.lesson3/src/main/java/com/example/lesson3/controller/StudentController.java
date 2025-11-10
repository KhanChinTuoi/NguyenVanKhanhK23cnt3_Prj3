package com.example.lesson3.controller;

import com.example.lesson3.entity.Student;
import com.example.lesson3.service.ServiceStudent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private ServiceStudent serviceStudent;

    @GetMapping("/student-list")
    public List<Student> getAllStudents() {
        return serviceStudent.getStudents();
    }

    @GetMapping("/student/{id}")
    public Student getAllStudents(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return serviceStudent.getStudent(param);
    }

    @PostMapping("/student-add")
    public Student addStudent(@RequestBody Student student) {
        return serviceStudent.addStudent(student);
    }

    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable String id,
                                 @RequestBody Student student) {
        Long param = Long.parseLong(id);
        return serviceStudent.updateStudent(param, student);
    }

    @DeleteMapping("/student/{id}")
    public boolean deleteStudent(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return serviceStudent.deleteStudent(param);
    }
}
