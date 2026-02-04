package com.nithiongodugu.FirstSB.service.impl;

import com.nithiongodugu.FirstSB.dto.AddStudentDto;
import com.nithiongodugu.FirstSB.dto.StudentDto;
import com.nithiongodugu.FirstSB.entity.Student;
import com.nithiongodugu.FirstSB.repository.StudentRepository;
import com.nithiongodugu.FirstSB.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found with id " + id));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto addNewStudent(AddStudentDto addStudentDto) {
        Student newStudent = modelMapper.map(addStudentDto, Student.class);
        Student student = studentRepository.save(newStudent);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("No student id found");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudentById(Long id, AddStudentDto addStudentDto) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid student id"));
        modelMapper.map(addStudentDto, student);

        studentRepository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto updateStudentById(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student id"));

        updates.forEach((field, value)->{
            switch (field){
                case "name":{
                    student.setName((String) value);
                    break;
                }
                case "email":{
                    student.setEmail((String) value);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Invalid field");
            }
        });

        studentRepository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

}
