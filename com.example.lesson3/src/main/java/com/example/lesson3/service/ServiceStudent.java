package com.example.lesson3.service;
import com.example.lesson3.entity.Student;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceStudent {List<Student> students = new ArrayList<>();
    public ServiceStudent() {
        students.addAll(Arrays.asList(
                new Student(1L," Nguyễn Văn Khánh,",20,"Male","Số 28 Đại Linh","0984915173","khanhvank9t727@gmail.com"),

        new Student(2L,"Nguyễn Văn A ",21,"Male","Số 18 Phùng Khoang","0934853785","nva@gmail.com"),
        new Student(3L,"Nguyễn Thị B",22,"Female","Ngõ 185 Đại Mỗ","0965748889","ntb@gmail.com")
));
    }
    // Lấy toàn bộ danh sách sinh viên
    public List<Student> getStudents() {
        return students;
    }
    // Lấy sinh viên theo id
    public Student getStudent(Long id) {
        return students.stream()
                .filter(student -> student
                        .getId().equals(id))
                .findFirst().orElse(null);
    }
    // Thêm mới một sinh viên
    public Student addStudent(Student student) {
        students.add(student);
        return student;
    }
    // Cập nhật thông tin sinh viên
    public Student updateStudent(Long id, Student student)
    {
        Student check = getStudent(id);
        if (check == null) {
            return null;
        }
        students.forEach(item -> {
            if (Objects.equals(item.getId(), id)) {
                item.setName(student.getName());
                item.setAddress(student.getAddress());
                item.setEmail(student.getEmail());
                item.setPhone(student.getPhone());
                item.setAge(student.getAge());
                item.setGender(student.getGender());
            }
        });
        return student;
    }
    // Xóa thông tin sinh viên
    public boolean deleteStudent(Long id){
        Student check = getStudent(id);
        return students.remove(check);
    }
}