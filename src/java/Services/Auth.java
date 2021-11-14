/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.ConsultaEspectaculoDTO;
import DTOs.LoginDTO;
import DTOs.RegistroDTO;
import DTOs.UserDTO;
import Logica.Clases.Usuario;
import Logica.Fabrica;
import Logica.Interfaz.IControladorUsuario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pabli
 */
@Path("auth")
public class Auth {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(LoginDTO user) {
        String userLogin = user.getUser();
        String passwdLogin = user.getPasswd();
        
        Map <String,Usuario> users = (Map <String,Usuario>) ICU.obtenerUsuarios();
        Usuario clientUser = users.get(userLogin);

        if(clientUser != null){
            if (ICU.login(userLogin, passwdLogin)==true) {
                UserDTO userDTO = new UserDTO(clientUser.getNickname(),clientUser.getNombre(),clientUser.getApellido(),clientUser.getEmail(),null);
                String tipo = ICU.esEspectador(clientUser.getNickname());
                if(tipo.equals("error")){
                    userDTO.setTipo("Error de usuario");
                }else{
                    userDTO.setTipo(tipo);
                }
                if(clientUser.getImagen().equals("")){
                    userDTO.setUrl_imagen("");
                }else{
                    userDTO.setUrl_imagen(clientUser.getImagen());
                }
                return Response.ok(userDTO, MediaType.APPLICATION_JSON).build();
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).build();
            } 
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @POST
    @Path("/registroEspectador")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarEspectador(RegistroDTO userRegistro) {

        Map <String,Usuario> users = (Map <String,Usuario>) ICU.obtenerUsuarios();
        Date fecha = userRegistro.getFecha();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimiento = sdf.format(fecha);
        if(users.get(userRegistro.getEmail())== null){
            if (ICU.addEspectador(userRegistro.getNickname(), userRegistro.getPass(),userRegistro.getEmail(), userRegistro.getNombre(), userRegistro.getApellido(), fechaNacimiento, userRegistro.getImagen())) {
                UserDTO userDTO = new UserDTO(userRegistro.getNickname(),userRegistro.getNombre(),userRegistro.getApellido(),userRegistro.getEmail(),userRegistro.getFecha());
                
                if(userRegistro.getImagen().equals("")){
                    userDTO.setUrl_imagen("");
                }else{
                    userDTO.setUrl_imagen(userRegistro.getImagen());
                }
                return Response.ok(userDTO, MediaType.APPLICATION_JSON).build();
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
//    @POST
//    @Path("/registroArtista")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response registrarArtista(RegistroDTO userRegistro) {
//
////        Map <String,Usuario> users = (Map <String,Usuario>) ICU.obtenerUsuarios();
////        
////        if(users.get(userRegistro.getEmail())== null){
////            if (ICU.addEspectador(userRegistro.getNickname(), userRegistro.getPass(),userRegistro.getEmail(), userRegistro.getNombre(), userRegistro.getApellido(), userRegistro.getFecha().toString(), userRegistro.getImagen())) {
////                UserDTO userDTO = new UserDTO(userRegistro.getNickname(),userRegistro.getNombre(),userRegistro.getApellido(),userRegistro.getEmail(),userRegistro.getFecha());
////                if(userRegistro.getImagen().equals("")){
////                    userDTO.setUrl_imagen("");
////                }else{
////                    userDTO.setUrl_imagen(userRegistro.getImagen());
////                }
////                return Response.ok(userDTO, MediaType.APPLICATION_JSON).build();
////            }else{
////                return Response.status(Response.Status.UNAUTHORIZED).build();
////            }
////        }else{
////            return Response.status(Response.Status.UNAUTHORIZED).build();
////        }
//
//    }
    
    @GET
    @Path("/consultaEspectaculo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaEspectaculo(ConsultaEspectaculoDTO espec) {
        return Response.ok(espec, MediaType.APPLICATION_JSON).build();
    }
    
}

            