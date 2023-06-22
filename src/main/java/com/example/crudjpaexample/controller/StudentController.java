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
    @ExceptionHandler
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        try {
            Student student = studentDao.findById(id);
            if (student == null) {
                throw new StudentNotFoundException("record not found!");
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (StudentNotFoundException ex) {
            StudentErrorResponse errorResponse = new StudentErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @return
     */
    @ExceptionHandler
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Student> studentList = studentDao.findAll();
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    ////////////////////////////////////////////////////////////////////

    /**
     * @param name
     * @return
     */
    @ExceptionHandler
    @GetMapping("find-by-name/{name}")
    public ResponseEntity<?> findAllByName(@PathVariable("name") String name) {
        try {
            List<Student> studentList = studentDao.findAllByName(name);
            if (studentList == null || studentList.size() == 0) {
                throw new StudentNotFoundException("no records found");
            }
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (StudentNotFoundException ex) {
            StudentErrorResponse errorResponse = new StudentErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    ////////////////////////////////////////////////////////////////////
    /**
     * @param student
     * @return
     */
    @ExceptionHandler
    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        boolean status = false;
        try {
            studentDao.save(student);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////////////////////////////////////////////////////////
    /**
     * @param id
     * @param student
     * @return
     */
    @ExceptionHandler
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentDao.update(student, id);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////////////////////////////////////////////////////////
    /**
     * @param id
     */
    @ExceptionHandler
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeStudent(@PathVariable("id") int id) {
        try {
            studentDao.removeById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
