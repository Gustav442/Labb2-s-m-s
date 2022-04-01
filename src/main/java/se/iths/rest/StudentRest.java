package se.iths.rest;


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
        List<Student> allStudents = studentService.getAllStudents();
        if (allStudents.isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.NO_CONTENT)
                    .entity("No students was found in database.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(allStudents).build();
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

        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Student with ID: " + id + " was not found in database.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(student).build();
    }



    @Path("update/{id}")
    @PATCH
    public Response updateFirstName(@PathParam("id") Long id,
                                    @QueryParam("firstname") String firstName) {
        Student updatedStudent = studentService.updateFirstName(id, firstName);
        return Response.ok(updatedStudent).build();
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

        if (students.isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .build());
        }

      return Response.ok(students).build();
    }


}
