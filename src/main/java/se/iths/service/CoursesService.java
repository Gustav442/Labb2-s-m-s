package se.iths.service;

import se.iths.entity.Course;
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
public class CoursesService {


    @PersistenceContext
    EntityManager entityManager;


    public List<Course> getAllCourses(){
        List<Course> courses = entityManager
                .createQuery("SELECT c FROM Course c", Course.class).getResultList();
    if (courses.isEmpty()){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setResponseCode("204");
        jsonResponse.setResponseStatus("No content!");
        jsonResponse.setResponseMessage("No courses was found in database.");
        throw new WebApplicationException(Response.status(200)
                .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
    }
    return courses;
    }

    public void createCoruse(Course course) {
    entityManager.persist(course);
    }

    public void deleteCourseById(Long id){
    Course course = findCourseById(id);
        entityManager.remove(course);

    }

    public Course findCourseById(Long id) {
    Course course = entityManager.find(Course.class, id);
    if (course == null){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setResponseCode("404");
        jsonResponse.setResponseStatus("Not found!");
        jsonResponse.setResponseMessage("Course with ID: " + id + " was not found in database.");
        throw new WebApplicationException(Response.status(404)
                .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
    }
    return course;
    }

    public Student findStudentById(Long id){
        Student student = entityManager.find(Student.class, id);
        if (student == null){
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("404");
            jsonResponse.setResponseStatus("Not found!");
            jsonResponse.setResponseMessage("Student with ID: " + id + " was not found in database.");
            throw new WebApplicationException(Response.status(404)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        return student;
    }


    public void addCourseToStudent(Long courseId, Long studentId) {
        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);

        course.addStudent(student);
        entityManager.persist(course);



    }
}
