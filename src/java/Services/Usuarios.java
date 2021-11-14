/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.ConsultaEspectaculoDTO;
import DTOs.UserDTO;
import Logica.Clases.Espectaculo;
import Logica.Clases.Funcion;
import Logica.Clases.Paquete;
import Logica.Clases.Usuario;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorPaquete;
import Logica.Interfaz.IControladorUsuario;
import java.util.Map;
import javax.validation.Valid;
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
@Path("usuarios")
public class Usuarios {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();

    @GET
    @Path("/")
    public Response getUsers(@QueryParam("nickname") String nickname) {
        try {

            Usuario usuario = ICU.obtenerEspectadorPorNick(nickname);
            System.out.println("Services.Users.getUsers()" + usuario.getNombre());
            UserDTO uDTO = new UserDTO();
            uDTO.setEmail(usuario.getEmail());
            uDTO.setNombre(usuario.getNombre());

            return Response.ok(uDTO, MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            System.out.println("Services.Users.getUsers()" + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDTO user) {
        return Response.ok("Welcomee to the jungle " + user.getNombre()).build();
    }
        
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    IControladorPaquete ICP = fabrica.getIControladorPaquete();

//    @GET
//    @Path("/espectaculo")
//    public Response getEspectaculoEspecífico(@QueryParam("nombre") String nombre) {
//        try {
//
//            Map<String, Espectaculo> escp = (Map<String, Espectaculo>) ICE.getEspectaculos();
//            Espectaculo espcSeleccionado = escp.get(nombre);
//
//            Map<String, Funcion> funcionesDeEspec = ICE.obtenerMapFunciones(nombre);
//            Map<String, Paquete> paquetes = (Map<String, Paquete>) ICP.getPaqueteDeEspectaculo(nombre);
//            //ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getCosto(), espcSeleccionado.getDuracion(), espcSeleccionado.getFecha(), espcSeleccionado.getCategorias(), espcSeleccionado.getUrlIamgen(), espcSeleccionado.getPlataforma(), espcSeleccionado.getEstado(), funcionesDeEspec, paquetes);
//            ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getCosto(), espcSeleccionado.getDuracion(), espcSeleccionado.getFecha(), espcSeleccionado.getCategorias(), espcSeleccionado.getUrlIamgen(), espcSeleccionado.getPlataforma(), espcSeleccionado.getEstado(), funcionesDeEspec, paquetes);
//            return Response.ok("test", MediaType.APPLICATION_JSON).build();
//
//        } catch (Exception e) {
//            System.out.println("Services.Users.getUsers()" + e.toString());
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//    }
}



