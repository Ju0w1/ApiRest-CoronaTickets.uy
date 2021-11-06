/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * @author pabli
 */
public class UserDTO implements Serializable {
    @NotNull
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private Date nacimiento;
    private String contrasenia;
    
    @Null
    private String url_imagen;
    private int seguidores;
    private int seguidos;
    
    //GETTERS
    public String getNickname() {
        return nickname;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public Date getNacimiento() {
        return nacimiento;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public String getUrl_imagen() {
        return url_imagen;
    }
    public int getSeguidores() {
        return seguidores;
    }
    public int getSeguidos() {
        return seguidos;
    }
    
    //SETTERS
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNacimiento(Date fechaNacimiento) {
        this.nacimiento = fechaNacimiento;
    }
    public void setContrasenia(String contra) {
        this.contrasenia = contra;
    }
    public void setUrl_imagen(String urlImagen) {
        this.url_imagen = urlImagen;
    }
    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }
    public void setSeguidos(int seguidos) {
        this.seguidos = seguidos;
    }
}
