package co.davidcastellanos.courseplatform.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String courseName;


    @NotEmpty(message = "El campo de instructor es obligatorio")
    @NotNull(message = "El instructor no puede tener valor nulo")
    private String instructor;


    //@NotEmpty(message = "El campo de capacidad es obligatorio")
    @NotNull(message = "El capacidad no puede tener valor nulo")
    private Integer capacitySignups;

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    static Date actualSignup;


    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "courses_has_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers;

    @Transient
    private static boolean userIsJoined;


    private Long userAssignedID;

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getCapacitySignups() {
        return capacitySignups;
    }

    public void setCapacitySignups(Integer capacitySignups) {
        this.capacitySignups = capacitySignups;
    }

    public Date getActualSignup() {
        return Course.actualSignup;
    }

    public void setActualSignup(Date actualSignup) {
        Course.actualSignup = actualSignup;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }



    public boolean getUserIsJoined() {
        return userIsJoined;
    }

    public void setUserIsJoined(boolean userIsJoined) {
        Course.userIsJoined = userIsJoined;
    }

    public Long getUserAssignedID() {
        return userAssignedID;
    }

    public void setUserAssignedID(Long userAssignedID) {
        this.userAssignedID = userAssignedID;
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
