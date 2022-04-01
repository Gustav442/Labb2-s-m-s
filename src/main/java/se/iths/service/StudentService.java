package se.iths.service;


import se.iths.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;


    public List<Student> getAllStudents() {
        return entityManager
                .createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public Student findStudentById(Long id) {
        Student student = entityManager
                .find(Student.class, id);
        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with ID: " + id + "was not found in database.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return student;
    }

    public void createStudent(Student student) {
        List<Student> students = getAllStudents();
        for (Student s : students) {
            if (s.getEmail().equals(student.getEmail())) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Email already exists.").type(MediaType.TEXT_PLAIN_TYPE).build());
            }
        }
        entityManager.persist(student);
    }

    public void deleteStudentById(Long id) {
        Student findStudent = findStudentById(id);
        if (findStudent == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with ID: " + id + " was not found in database.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        entityManager.remove(findStudent);
    }

    public Student updateFirstName(Long id, String firstName) {
        Student student = findStudentById(id);

        student.setFirstName(firstName);
        return student;
    }

    public Student updateEmail(Long id, String email) {
        List<Student> students = getAllStudents();
        Student student = findStudentById(id);

        for (Student s : students) {
            if (s.getEmail().equals(email)) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Email already exists.").type(MediaType.TEXT_PLAIN_TYPE).build());
            }
        }
        student.setEmail(email);
        return student;
    }

    public List<Student> getLastName(String lastName) {
        return entityManager
                .createQuery("SELECT s FROM Student s WHERE s.lastName ='" + lastName + "'", Student.class)
                .getResultList();
    }

}



