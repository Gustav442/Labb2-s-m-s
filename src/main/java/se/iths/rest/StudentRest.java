package se.iths.rest;


import se.iths.entity.JSONResponse;
import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {

    StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }


    @Path("create")
    @POST
    public Response createStudent(Student student) {
        studentService.createStudent(student);
        return Response.status(Response.Status.CREATED)
                .entity(student).build();
    }

    @Path("")
    @GET
    public Response getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setResponseCode("204");
            jsonResponse.setResponseStatus("No content!");
            jsonResponse.setResponseMessage("No students was found in database.");
            throw new WebApplicationException(Response.status(200)
                    .entity(jsonResponse).type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        return Response.ok(students).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteStudentById(@PathParam("id") Long id) {
        studentService.deleteStudentById(id);
        return Response.ok().build();
    }

    @Path("{id}")
    @GET
    public Response findStudentById(@PathParam("id") Long id) {
        Student student = studentService.findStudentById(id);
        return Response.ok(student).build();
    }



    @Path("update/{id}")
    @PATCH
    public Response updateFirstName(@PathParam("id") Long id,
                                    @QueryParam("firstname") String firstName) {
        Student student = studentService.updateFirstName(id, firstName);
        return Response.status(200)
                .entity(student).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Path("update/email/{id}")
    @PATCH
    public Response updateEmail(@PathParam("id") Long id, Student student) {
        Student updatedStudent = studentService.updateEmail(id, student.getEmail());
        return Response.ok(updatedStudent).build();
    }

    @Path("getlastname")
    @GET
    public Response getLastName(@QueryParam("lastName") String lastName){
      List<Student> students = studentService.getLastName(lastName);
      return Response.ok(students).build();
    }


}
