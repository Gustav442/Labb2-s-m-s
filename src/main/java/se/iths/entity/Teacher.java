package se.iths.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    private String firstName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    private String lastName;

    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Column(name = "EMAIL", nullable = false, length = 80)
    private String email;



@OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course){
    courses.add(course);
    course.setTeacher(this);
}

    public Teacher(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Teacher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
