package co.davidcastellanos.courseplatform.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUser {
    @NotEmpty(message = "El campo de email es obligatorio")
    @NotNull(message = "El email no puede tener valor nulo")
    @Size(min = 6, max = 40, message = "El email debe tener entre 6 y 40 caracteres")
    @Email(message = "Ingrese un correo válido")
    private String loginEmail;

    @NotEmpty(message = "El campo de password es obligatorio")
    @NotNull(message = "El password no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String loginPassword;

    public LoginUser() {
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
