/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaPaqueteDTO;
import Logica.Fabrica;
import Logica.Interfaz.IControladorUsuario;
import Logica.Interfaz.IControladorPaquete;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pabli
 */
@Path("paquetes")
public class Paquetes {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    IControladorPaquete ICP = fabrica.getIControladorPaquete();
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaPaquete(AltaPaqueteDTO paq) {
        if (ICP.isNombrePaqueteUsado(paq.getNombre()) == true) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }else{
            ICP.altaPaquete(paq.getNombre(),paq.getFechaInicio(),paq.getFechaFin(),paq.getFechaCreado(),paq.getDescuento(),paq.getDescipcion(),paq.getImagen());
            return Response.ok().build();
        }
    }
}
