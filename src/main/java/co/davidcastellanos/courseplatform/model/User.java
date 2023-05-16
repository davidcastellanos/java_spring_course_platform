package co.davidcastellanos.courseplatform.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String name;


    @NotEmpty(message = "El campo de email es obligatorio")
    @NotNull(message = "El email no puede tener valor nulo")
    @Size(min = 6, max = 40, message = "El email debe tener entre 6 y 40 caracteres")
    @Email(message = "Ingrese un correo válido")
    private String email;


    @NotEmpty(message = "El campo de contraseña es obligatorio")
    @NotNull(message = "El password no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String password;

    @Transient
    @NotEmpty(message = "El campo de contraseña es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String confirmPassword;

    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;


    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Course> myCreatedCourses;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "courses_has_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> myAssignedCourses;


    public User() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Course> getMyCreatedCourses() {
        return myCreatedCourses;
    }

    public void setMyCreatedCourses(List<Course> myCreatedCourses) {
        this.myCreatedCourses = myCreatedCourses;
    }

    public List<Course> getMyAssignedCourses() {
        return myAssignedCourses;
    }

    public void setMyAssignedCourses(List<Course> myAssignedCourses) {
        this.myAssignedCourses = myAssignedCourses;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
