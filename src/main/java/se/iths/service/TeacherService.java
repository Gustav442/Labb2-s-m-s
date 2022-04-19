package se.iths.service;

import se.iths.entity.Course;
import se.iths.entity.JSONResponse;
import se.iths.entity.Student;
import se.iths.entity.Teacher;

import javax.ejb.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Transactional
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public List<Teacher> getAllTeachers(){
        return entityManager
                .createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }

    public void createTeacher(Teacher teacher) {
        if (!createTeacherValid(teacher)){
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("400");
            jsonResponse.setResponseStatus("Bad request!");
            jsonResponse.setResponseMessage("first name, last name or email can't be empty!");
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        entityManager.persist(teacher);
    }

    public void deleteTeacherById(Long id){
        Teacher teacher = findTeacherById(id);
        entityManager.remove(teacher);

    }

    public Teacher findTeacherById(Long id) {
       Teacher teacher = entityManager.find(Teacher.class, id);

        if (teacher == null){
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("404");
            jsonResponse.setResponseStatus("Not found!");
            jsonResponse.setResponseMessage("Teacher with ID: " + id + " was not found in database.");
            throw new WebApplicationException(Response.status(404)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        return teacher;
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

    public void addCourseToTeacher(Long courseId, Long teacherId) {

        Course course = findCourseById(courseId);
        Teacher teacher = findTeacherById(teacherId);
        teacher.addCourse(course);

        entityManager.persist(teacher);

    }

    public boolean createTeacherValid(Teacher teacher) {
        if (teacher.getFirstName() == null
                || teacher.getLastName() == null
                || teacher.getEmail() == null) {
            return false;
        }
        return true;
    }
}

