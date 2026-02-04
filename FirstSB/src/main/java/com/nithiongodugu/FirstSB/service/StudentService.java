package com.nithiongodugu.FirstSB.service;

import com.nithiongodugu.FirstSB.dto.AddStudentDto;
import com.nithiongodugu.FirstSB.dto.StudentDto;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    StudentDto addNewStudent(AddStudentDto addStudentDto);

    void deleteStudentById(Long id);

    StudentDto updateStudentById(Long id, AddStudentDto addStudentDto);

    StudentDto updateStudentById(Long id, Map<String, Object> updates);
}
