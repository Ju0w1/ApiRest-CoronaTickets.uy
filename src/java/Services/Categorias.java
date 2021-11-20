/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.TransporteListaCategoriasDTO;
import Logica.Clases.Categoria;
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
@Path("categorias")
public class Categorias {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPlataformas() {
        
        try {
            Map<String, Categoria> categorias = (Map<String, Categoria>) ICE.getCategorias();
            List<String> categs = new ArrayList<>();
            categorias.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                categs.add(value.getNombre());
            });
            TransporteListaCategoriasDTO cat = new TransporteListaCategoriasDTO(categs);
            return Response.ok(cat, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
}
