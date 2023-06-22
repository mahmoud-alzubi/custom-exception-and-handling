package com.example.crudjpaexample.dao;

import com.example.crudjpaexample.entity.Student;

import java.util.List;

public interface StudantDao {
    public Student findById(int id);

    public List<Student> findAll();

    public List<Student> findAllByName(String name);

    public void save(Student student);

    public Student update (Student student, int id);

    public void removeById (int id);

}
