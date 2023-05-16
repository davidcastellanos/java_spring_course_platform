package co.davidcastellanos.courseplatform.controller;


import co.davidcastellanos.courseplatform.model.Course;
import co.davidcastellanos.courseplatform.model.LoginUser;
import co.davidcastellanos.courseplatform.model.User;
import co.davidcastellanos.courseplatform.service.CourseService;
import co.davidcastellanos.courseplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    /*OBTENER VISTA DE REGISTRO Y LOGIN*/
    @GetMapping("/")
    public String index(@ModelAttribute("nuevoUser") User nuevoUser,
                        @ModelAttribute("nuevoLogin") LoginUser loginUser) {
        return "index.jsp";
    }

    /*PASAR OBJETO CON DATOS DE REGISTRO DESDE FORMULARIO PARA CREAR NUEVA CUENTA*/
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("nuevoUser") User nuevoUser,
                               BindingResult result,
                               Model model,
                               HttpSession session) {

        userService.registerUser(nuevoUser, result);

        if (result.hasErrors()) {
            model.addAttribute("nuevoLogin", new LoginUser());
            return "index.jsp";
        } else {
            session.setAttribute("userSession", nuevoUser);
            return "redirect:/courses";
        }

    }

    /*ENVIAR OBJETO CON DATOS DE REGISTRO DESDE FORMULARIO PARA VERIFICAR E INGRESAR A CUENTA EXISTENTE*/
    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("nuevoLogin") LoginUser nuevoLogin,
                            BindingResult result,
                            Model model,
                            HttpSession session) {

        User user = userService.loginUser(nuevoLogin, result);
        if (result.hasErrors()) {
            model.addAttribute("nuevoUser", new User());
            return "index.jsp";
        }

        session.setAttribute("userSession", user);
        return "redirect:/courses";
    }


    /*VISTA DE DASHBOARD DE CURSOS CON TODOS LOS CURSOS EXISTENTES Y DETALLES*/
    @GetMapping("/courses")
    public String dashBoard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        List<Course> allCoursesList = courseService.findAndReturnAllCourses();
        model.addAttribute("allCoursesList", allCoursesList);
        // proyectos a los que pertenece



        return "courses.jsp";
    }


    /*VISTA DE DASHBOARD DE CURSOS CON TODOS LOS CURSOS EXISTENTES Y DETALLES ORDENADOS POR CAPACIDAD MAYOR A MENOR*/
    @GetMapping("/courses/mayorAmenor")
    public String dashBoardMayorAMenor(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        List<Course> allCoursesList = courseService.findAndReturnAllCourses();
        Collections.sort(allCoursesList, new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return course2.getCapacitySignups() - course1.getCapacitySignups();
            }
        });

        model.addAttribute("allCoursesList", allCoursesList);
        // proyectos a los que pertenece



        return "courses.jsp";
    }


    /*VISTA DE DASHBOARD DE CURSOS CON TODOS LOS CURSOS EXISTENTES Y DETALLES ORDENADOS POR CAPACIDAD MENOR A MAYOR*/
    @GetMapping("/courses/menorAmayor")
    public String dashBoardMenorAMayor(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        List<Course> allCoursesList = courseService.findAndReturnAllCourses();
        Collections.sort(allCoursesList, new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return course1.getCapacitySignups() - course2.getCapacitySignups();
            }
        });

        model.addAttribute("allCoursesList", allCoursesList);
        // proyectos a los que pertenece



        return "courses.jsp";
    }



    /*CERRAR SESIÓN DE USUARIO LOGUEADO*/
    @GetMapping("/logout")
    public String logOutUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null ) {
            return "redirect:/";
        } else {
            session.removeAttribute("userSession");
            return "redirect:/";
        }
    }





}
