/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaPremioDTO;
import Logica.Fabrica;
import Logica.Interfaz.IControladorPremio;
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
    IControladorPremio ICF = fabrica.getIControladorPremio();
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaEspectaculo(AltaPremioDTO premio) {
        if (ICF.addPremio(premio.getDescripcion(), premio.getNomEspec(), premio.getCantidad()) == true) {
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
