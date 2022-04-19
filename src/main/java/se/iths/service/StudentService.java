package se.iths.service;


import org.apache.commons.validator.routines.EmailValidator;
import se.iths.entity.JSONResponse;
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
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("404");
            jsonResponse.setResponseStatus("Not found!");
            jsonResponse.setResponseMessage("Student with ID: " + id + " was not found in database.");
            throw new WebApplicationException(Response.status(404)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        return student;
    }

    public void createStudent(Student student) {
        List<Student> students = getAllStudents();
        for (Student s : students) {
            if (s.getEmail().equals(student.getEmail())) {
                JSONResponse jsonResponse = new JSONResponse();
                jsonResponse.setResponseCode("400");
                jsonResponse.setResponseStatus("Bad request!");
                jsonResponse.setResponseMessage("Email already exists!");
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
            }
        }if (student.getEmail().isBlank()
                || !isEmailValid(student.getEmail())) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("400");
            jsonResponse.setResponseStatus("Bad request!");
            jsonResponse.setResponseMessage("Give a correct email. Example: Correct@gmail.com");
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        } else if (!createStudentValid(student)){
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("400");
            jsonResponse.setResponseStatus("Bad request!");
            jsonResponse.setResponseMessage("first name, last name or email can't be empty!");
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
            entityManager.persist(student);
    }

    public void deleteStudentById(Long id) {
        Student student = findStudentById(id);
        entityManager.remove(student);
    }

    public Student updateFirstName(Long id, String firstName) {
        Student student = findStudentById(id);
        if (firstName.isBlank() || firstName.length() < 2) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("400");
            jsonResponse.setResponseStatus("Bad request!");
            jsonResponse.setResponseMessage("Name need atleast two letters");
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        student.setFirstName(firstName);
        return student;
    }

    public Student updateEmail(Long id, String email) {
        List<Student> students = getAllStudents();
        Student student = findStudentById(id);

        for (Student s : students) {
            if (s.getEmail().equals(email)) {
                JSONResponse jsonResponse = new JSONResponse();
                jsonResponse.setResponseCode("400");
                jsonResponse.setResponseStatus("Bad request!");
                jsonResponse.setResponseMessage("Email already exists!");
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
            } else if (email.isBlank()
                    || !isEmailValid(email)) {
                JSONResponse jsonResponse = new JSONResponse();
                jsonResponse.setResponseCode("400");
                jsonResponse.setResponseStatus("Bad request!");
                jsonResponse.setResponseMessage("Give a correct email.");
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
            }
        }
        student.setEmail(email);
        return student;
    }

    public List<Student> getLastName(String lastName) {
        List<Student> students = entityManager
                .createQuery("SELECT s FROM Student s WHERE s.lastName ='" + lastName + "'", Student.class)
                .getResultList();
        if (students.isEmpty()) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("404");
            jsonResponse.setResponseStatus("Not found!");
            jsonResponse.setResponseMessage("Student with last name: " + lastName + " was not found in database.");
            throw new WebApplicationException(Response.status(404)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        return students;

    }

    public boolean createStudentValid(Student student) {
        if (student.getFirstName() == null
        || student.getLastName() == null
        || student.getEmail() == null) {
            return false;
        }
        return true;
    }

    public boolean isEmailValid(String email) {

        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }


}



