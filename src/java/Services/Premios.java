/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaPremioDTO;
import DTOs.DarPremioDTO;
import DTOs.ListTrophyDTO;
import DTOs.TrophyDTO;
import DTOs.UserDTO;
import DTOs.funcionDTOSimple;
import DTOs.numeroDTO;
import Logica.Fabrica;
import Logica.Interfaz.IControladorPremio;
import Logica.Clases.Premio;
import Logica.Interfaz.IControladorFuncion;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pabli
 */
@Path("premios")
public class Premios {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorPremio ICP = fabrica.getIControladorPremio();
    IControladorFuncion ICF = fabrica.getIControladorFuncion();
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaEspectaculo(AltaPremioDTO premio) {
        if (ICP.addPremio(premio.getDescripcion(), premio.getNomEspec(), premio.getCantidad()) == true) {
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    @POST
    @Path("/getPremiosDeUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiosDeUser(UserDTO user) {
//        if (fabrica.getIControladorUsuario().obtenerEspectadorPorNick(user.getNickname())!=null){
            List<Premio> premios = fabrica.getIControladorPremio().getPremiosDeEspectador(user.getNickname());
            List<TrophyDTO> premiosDTO = new ArrayList<>();
            
            for (Premio premio :  premios) {
                premiosDTO.add(new TrophyDTO(premio.getEspectador().getNombre(), premio.getDescripcion(), premio.getFuncion().getNombre(), premio.getFecha()));
            }
            ListTrophyDTO premiosX= new ListTrophyDTO(premiosDTO);
            return Response.ok(premiosX, MediaType.APPLICATION_JSON).build();
        }
    
    
    @POST
    @Path("/getCantidadPremios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCantidadPremios(funcionDTOSimple funcion) {
        String nombreFuncion = funcion.getNombreFuncion();
        int cantidad = 0;
        cantidad = ICF.obtenerCantidadDePremiosASortear(nombreFuncion);
        numeroDTO cant = new numeroDTO(cantidad);
        return Response.ok(cant, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/otorgarPremio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCantidadPremios(DarPremioDTO sorteado) {
        try {
           ICP.otorgarPremio(sorteado.getNomFuncion(), sorteado.getNickname());
           return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
//    }
}
