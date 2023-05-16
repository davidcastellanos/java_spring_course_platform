package co.davidcastellanos.courseplatform.service;


import co.davidcastellanos.courseplatform.model.Course;
import co.davidcastellanos.courseplatform.model.LoginUser;
import co.davidcastellanos.courseplatform.model.User;
import co.davidcastellanos.courseplatform.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;

    /*CREACIÓN DE UN NUEVO USUARIO EN DB*/
    public User registerUser(User nuevoUser, BindingResult result) {
        if (!nuevoUser.getPassword().equals(nuevoUser.getConfirmPassword())) {
            result.rejectValue("password", "Matches", "Las contraseñas no coinciden, intenta de nuevo");
        }

        String nuevoEmail = nuevoUser.getEmail();
        if (userRepository.findByEmail(nuevoEmail).isPresent()) {
            result.rejectValue("email", "Unique", "El email ya fue ingresado previamente");
        }

        if (result.hasErrors()) {
            return null;
        } else {
            // Encriptar password ingresado
            String passwordEncrypted = BCrypt.hashpw(nuevoUser.getPassword(), BCrypt.gensalt());
            // Actualizar dicho password encriptado en el objeto user
            nuevoUser.setPassword(passwordEncrypted);
            // guardar objeto user actualizado
            return userRepository.save(nuevoUser);
        }

    }

    /*VERIFICACIÓN Y LOGIN DE UN USUARIO EN DB*/
    public User loginUser(LoginUser nuevoLogin, BindingResult result) {
        Optional<User> posibleUsuario = userRepository.findByEmail(nuevoLogin.getLoginEmail());

        if (!posibleUsuario.isPresent()) {
            result.rejectValue("email", "Unique", "El Correo NO está registrado");
            return null;
        }

        User userLogin = posibleUsuario.get();
        if (!BCrypt.checkpw(nuevoLogin.getLoginPassword(), userLogin.getPassword())) {
            result.rejectValue("password", "Matches", "La contraseña ingresada no coincide, intenta de nuevo");
        }


        if (result.hasErrors()) {
            return null;
        } else {
            return userLogin;
        }
    }

    /*OBTENER OBJETO USUARIO A TRAVÉS DE UN ID NUMÉRICO*/
    public User findUserUsingID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /*GUARDAR OBJETO USUARIO EN DB*/
    public User saveUser(User user) {
        return userRepository.save(user);
    }



    /*AGREGAR UN USUARIO A UN NUEVO CURSO EMPAREJÁNDOLOS A TRAVÉS DE SUS ID Y ACTUALIZANDO EL CURSO MODIFICADO*/
    public void joinUserToCourse(Long courseId, Long userId) {
        User userFound = findUserUsingID(userId);
        Course courseFound = courseService.findCourseUsingID(courseId);


        courseFound.setUserIsJoined(true);
        courseFound.setUserAssignedID(userId);
        courseFound.getAssignedUsers().add(userFound);
        courseFound.setActualSignup(new Date());


        courseService.saveCourse(courseFound);


    }

    /*ELIMINAR UN USUARIO A UN NUEVO CURSO EMPAREJÁNDOLOS A TRAVÉS DE SUS ID Y ACTUALIZANDO EL CURSO MODIFICADO*/
    public void removeUserToCourse(Long courseId, Long userId) {
        User userFound = findUserUsingID(userId);
        Course courseFound = courseService.findCourseUsingID(courseId);


        courseFound.setUserIsJoined(false);
        courseFound.setUserAssignedID(0L);
        courseFound.getAssignedUsers().remove(userFound);

        courseService.saveCourse(courseFound);

    }


}
