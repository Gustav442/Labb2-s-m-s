package se.iths.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2)
    private String subject;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
  private List<Student> connectedStudents = new ArrayList<>();


    public Course(String subject) {
        this.subject = subject;
    }

    public Course() {

    }
    public Teacher getTeacher() {
        return teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public List<Student> getStudents() {
        return connectedStudents;
    }


    public void addStudent(Student student) {
        connectedStudents.add(student);
        student.getCourses().add(this);

    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course(Teacher teacher) {
        this.teacher = teacher;
    }
}
