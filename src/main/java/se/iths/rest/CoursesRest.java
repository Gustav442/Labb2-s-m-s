package se.iths.rest;

import se.iths.entity.Course;
import se.iths.entity.JSONResponse;
import se.iths.service.CoursesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoursesRest {

    CoursesService coursesService;

    @Inject
    public CoursesRest(CoursesService coursesService) {
        this.coursesService = coursesService;
    }


    @Path("create")
    @POST
    public Response createCourse(Course course) {
        coursesService.createCoruse(course);
        return Response.status(Response.Status.CREATED)
                .entity(course).build();
    }

    @Path("{id}")
    @GET
    public Response findCourseById(@PathParam("id") Long id) {
        Course course = coursesService.findCourseById(id);
        return Response.ok(course).build();
    }

    @Path("")
    @GET
    public Response getAllCourses() {
        List<Course> courses = coursesService.getAllCourses();
        return Response.ok(courses).build();
    }


    //TODO fix response
    @Path("{id}")
    @DELETE
    public Response deleteCourse(@PathParam("id") Long id) {
        coursesService.deleteCourseById(id);
        return Response.ok().build();
    }


    //TODO fix response
    @Path("CourseStudent/{courseId}/{studentId}")
    @PUT
    public Response addCourseToStudent(@PathParam("courseId")Long courseId,
    @PathParam("studentId")Long studentId){

     coursesService.addCourseToStudent(courseId, studentId);

        return Response.ok().build();

    }
}


