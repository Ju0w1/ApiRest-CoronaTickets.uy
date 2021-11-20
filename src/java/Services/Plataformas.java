/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.TransporteListaPlataformasDTO;
import Logica.Clases.Plataforma;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pabli
 */
@Path("plataformas")
public class Plataformas {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPlataformas() {
        
        try {
            Map<String, Plataforma> plataformas = (Map<String, Plataforma>) ICE.getPlataformas();
            List<String> plats = new ArrayList<>();
            plataformas.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                plats.add(value.getNombre());
            });
            TransporteListaPlataformasDTO pla = new TransporteListaPlataformasDTO(plats);
            return Response.ok(pla, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
