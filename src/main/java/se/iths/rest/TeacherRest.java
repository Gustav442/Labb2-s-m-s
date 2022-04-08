package se.iths.rest;

import se.iths.entity.JSONResponse;
import se.iths.entity.Teacher;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    TeacherService teacherService;

    @Inject
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Path("create")
    @POST
    public Response createTeacher(Teacher teacher) {
        teacherService.createTeacher(teacher);
        return Response.status(201)
                .entity(teacher).build();
    }

    @Path("{id}")
    @GET
    public Response findTeacherById(@PathParam("id") Long id) {
        Teacher teacher = teacherService.findTeacherById(id);

        if (teacher == null) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("404");
            jsonResponse.setResponseStatus("Not found!");
            jsonResponse.setResponseMessage("Teacher with ID: " + id + " was not found in database.");
            return Response.status(404)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.ok(teacher).build();
    }

    @Path("")
    @GET
    public Response getAllTeacher() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("204");
            jsonResponse.setResponseStatus("No content!");
            jsonResponse.setResponseMessage("No teacher was found in database.");
            return Response.status(200)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.ok(teachers).build();
    }

//TODO fix response
    @Path("{id}")
    @DELETE
    public Response deleteTeacher(@PathParam("id") Long id) {
        teacherService.deleteTeacherById(id);
        return Response.ok().build();
    }

    //TODO fix response
    @Path("addCourseToTeacher/{courseId}/{teacherId}")
    @PUT
    public Response addCourseToStudent(@PathParam("courseId") Long courseId,
                                       @PathParam("teacherId") Long teacherId) {

        teacherService.addCourseToTeacher(courseId, teacherId);

        return Response.ok().build();

    }
}