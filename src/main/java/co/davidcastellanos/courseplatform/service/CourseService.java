package co.davidcastellanos.courseplatform.service;

import co.davidcastellanos.courseplatform.model.Course;
import co.davidcastellanos.courseplatform.model.User;
import co.davidcastellanos.courseplatform.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;


    Course course;


    /*GUARDAR UN CURSO EN LA DB*/
    public Course saveCourse(Course nuevoCourse) {
        return courseRepository.save(nuevoCourse);
    }


    /*OBTENIENDO LA LISTA DE TODOS LOS CURSOS EXISTENTE EN LA DB*/
    public List<Course> findAndReturnAllCourses() {
        return courseRepository.findAll();
    }


    /*ENCONTRAR EN DB LOS CURSOS EMPAREJADOS A UN USUARIO DADO */
    public List<Course> findMyAssignedCourses(User userInSession) {
        return courseRepository.findAllByAssignedUsers(userInSession);
    }


    /*ENCONTRAR EN DB LOS CURSOS NO EMPAREJADOS A UN USUARIO DADO */
    public List<Course> findOtherNotAssignedCourses(User userInSession) {
        return courseRepository.findByAssignedUsersNotContains(userInSession);
    }

    /*ENCONTRAR UN CURSO EN LA DB ESPECIFICO A TRAVÉS DE UN ID NUMÉRICO DADO */
    public  Course findCourseUsingID(Long id) {
        return courseRepository.findById(id).orElse(null);
    }


    /*ELIMINAR UN CURSO ESPECIFICO EN LA DB A TRAVÉS DE UN ID NUMÉRICO DADO */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }


}
