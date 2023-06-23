package com.example.crudjpaexample.controller;

import com.example.crudjpaexample.dao.StudentDaoImpl;
import com.example.crudjpaexample.entity.Student;
import com.example.crudjpaexample.model.Model;
import com.example.crudjpaexample.model.StudentErrorResponse;
import com.example.crudjpaexample.model.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentDaoImpl studentDao;

    @Autowired
    public StudentController(StudentDaoImpl studentDao) {
        this.studentDao = studentDao;
    }


    /////////////////////////////////////////////////////////////////////

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        Student student = studentDao.findById(id);
        if (student == null) {
            throw new StudentNotFoundException("record not found!");
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Student> studentList = studentDao.findAll();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param name
     * @return
     */
    @GetMapping("find-by-name/{name}")
    public ResponseEntity<?> findAllByName(@PathVariable("name") String name) {
        List<Student> studentList = studentDao.findAllByName(name);
        if (studentList == null || studentList.size() == 0) {
            throw new StudentNotFoundException("no records found");
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param student
     * @return
     */
    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        studentDao.save(student);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param id
     * @param student
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        Student updatedStudent = studentDao.update(student, id);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeStudent(@PathVariable("id") int id) {
        studentDao.removeById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////

    /**
     * @param e
     * @return
     */
    @ExceptionHandler
    ResponseEntity<?> handleException(StudentNotFoundException e) {
        StudentErrorResponse errorResponse = new StudentErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param e
     * @return
     */
    @ExceptionHandler
    ResponseEntity<?> handleException(Exception e) {
        StudentErrorResponse errorResponse = new StudentErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
