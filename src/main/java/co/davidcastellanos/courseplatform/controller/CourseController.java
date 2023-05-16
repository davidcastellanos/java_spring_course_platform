package co.davidcastellanos.courseplatform.controller;

import co.davidcastellanos.courseplatform.model.Course;
import co.davidcastellanos.courseplatform.model.User;
import co.davidcastellanos.courseplatform.service.CourseService;
import co.davidcastellanos.courseplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    /*OBTENER VISTA DE FORM PARA CREACIÓN DE OBJETO CURSO*/
    @GetMapping("/new")
    public String viewNewCourse(@ModelAttribute("course") Course course,
                              HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        return "new.jsp";
    }


    /*ENVIANDO DATOS A DB A TRAVÉS DE FORM PARA CREACIÓN DE OBJETO CURSO*/
    @PostMapping("/new")
    public String createTask(@Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             HttpSession session) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        /* Revisar la sesión FIN*/


        //REVISAR ERRORES DEL REGISTRO
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
            //guardamos el CURSO NUEVO EN DB
            courseService.saveCourse(course);

            //Actualizar usuario propietario
            User myUser = userService.findUserUsingID(currentUser.getId());
            //myUser.getMyAssignedCourses().add(newAssignedCourse);// se agrega proyecto a usuario
            userService.saveUser(myUser);

            return "redirect:/courses";
        }

    }

    /*Vincularse a un curso a través de ID del curso y de ID de usuario logueado*/
    @GetMapping("/join/{courseId}")
    public String join(@PathVariable("courseId") Long courseId,
                       HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        /*MÉTODO EN EL SERVICE QUE NOS UNA AL CURSO*/
        userService.joinUserToCourse(courseId, currentUser.getId());
        return "redirect:/courses/show/{courseId}";

    }



    /*Desvincularse a un curso a través de ID del curso y de ID de usuario logueado*/
    @GetMapping("/leave/{courseId}")
    public String leave(@PathVariable("courseId") Long courseId,
                        HttpSession session) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        /*MÉTODO EN EL SERVICE QUE NOS ELIMINE DE UN CURSO*/
        userService.removeUserToCourse(courseId, currentUser.getId());//service.removeProjectUser(projectId, currentUser.getId());
        return "redirect:/courses/show/{courseId}";
    }



    /*VISTA DE UN CURSO ACTUAL Y PASO DE ATRUIBUTOS PARA DEFINIR LO QUE SE MUESTRA EN JSP*/
    @GetMapping("/show/{id}")
    public String dashBoard(@PathVariable("id") Long id,
                            HttpSession session,
                            Model model) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        /* Revisar la sesión FIN*/

        Course courseFound = courseService.findCourseUsingID(id);
        //System.out.println("courseFound = " + courseFound);
        if (courseFound == null) {
            return "redirect:/";
        }

        //Cursos asignados a al usuario logueado
        List<Course> myAssignedCoursesList = courseService.findMyAssignedCourses(currentUser);
        model.addAttribute("myAssignedCoursesList", myAssignedCoursesList);
        //System.out.println("myAssignedCoursesList = " + myAssignedCoursesList);

        //Cursos NOasignados a al usuario logueado
        List<Course> otherCoursesList = courseService.findOtherNotAssignedCourses(currentUser);
        model.addAttribute("otherNotAssignedCourses", otherCoursesList);
        //System.out.println("otherCoursesList = " + otherCoursesList);

        model.addAttribute("courseFound", courseFound);
        return "courseDetail.jsp";
    }



    /*VISTA DEL FORM QUE PERMITIRÁ EDITAR Y ACTUALIZAR UN CURSO EN DB*/
    @GetMapping("/edit/{courseId}")
    public String editCourse(@PathVariable("courseId") Long courseId,
                       HttpSession session,
                       @ModelAttribute("task") Course course,
                       Model model) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/


        Course courseToEdit = courseService.findCourseUsingID(courseId);

        /*REVISIÓN DE QUE ID DE LEAD COINCIDA CON EL DE LA SESIÓN*/
        if (currentUser.getId() != courseToEdit.getOwner().getId()) {
            return "redirect:/";
        }

        model.addAttribute("courseToEdit", courseToEdit);

        return "courseEdit.jsp";

    }


    /*CAPTURA DE UN CURSO POR ID Y ACTUALIZACIÓN DE SUS DATOS EN DB USANDO SETTERS, GUARDANDO ACTUALIZADO*/
    @PutMapping("/edit/{courseId}")
    public String updateCourse(@PathVariable("courseId") Long courseId,
                             @Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        if (result.hasErrors()){
            return "courseEdit.jsp";
        } else {

            Course existingCourse = courseService.findCourseUsingID(courseId);
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setInstructor(course.getInstructor());
            existingCourse.setCapacitySignups(course.getCapacitySignups());


            courseService.saveCourse(existingCourse);
            return  "redirect:/courses";
        }
    }


    /*ELIMINAR POR COMPLETO UN CURSO DE LA DB*/
    @DeleteMapping("/delete/{id}")
    public String deleteCourseUsingID(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return  "redirect:/courses";
    }



}
