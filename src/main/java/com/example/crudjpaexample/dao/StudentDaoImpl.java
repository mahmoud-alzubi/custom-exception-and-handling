package com.example.crudjpaexample.dao;

import com.example.crudjpaexample.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoImpl implements StudantDao {

    private EntityManager entityManager;


    /////////////////////////////////////////////////////////

    /**
     * @param entityManager
     */
    @Autowired
    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    //////////////////////////////////////////////////////////

    /**
     * @return
     */
    @Override
    public List<Student> findAll() {
        TypedQuery<Student> studentTypedQuery = entityManager.createQuery("FROM Student", Student.class);
        List<Student> studentList = studentTypedQuery.getResultList();
        return studentList;
    }

    //////////////////////////////////////////////////////////

    /**
     * @param name
     * @return
     */
    @Override
    public List<Student> findAllByName(String name) {
        TypedQuery<Student> studentTypedQuery = entityManager.createQuery("FROM Student WHERE firstName = :firstName ", Student.class);
        studentTypedQuery.setParameter("firstName", name);
        List<Student> studentList = studentTypedQuery.getResultList();
        return studentList;
    }

    //////////////////////////////////////////////////////////

    /**
     * @param id
     * @return
     */
    @Override
    public Student findById(int id) {
        Student student = entityManager.find(Student.class, id);
        return student;
    }

    /////////////////////////////////////////////////////////

    /**
     * @param student
     */
    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    @Transactional
    public Student update(Student student, int id) {
//        student.setId(id);
//        entityManager.merge(student);

        Query query = entityManager.createQuery("UPDATE Student set firstName = :fName, lastName =  :lName, email = :email WHERE id = :id");
        query.setParameter("fName", student.getFirstName());
        query.setParameter("lName", student.getLastName());
        query.setParameter("email", student.getEmail());
        query.setParameter("id", id);

        query.executeUpdate();

        Student updatedStudent = findById(id);
        return updatedStudent;
    }

    @Override
    @Transactional
    public void removeById(int id) {

//        Query query = entityManager.createQuery("DELETE FORM Student where id = :id");
//        query.setParameter("id", id);
//        int numOfRows = query.executeUpdate();

        Student student = findById(id);
        entityManager.remove(student);
    }
}
