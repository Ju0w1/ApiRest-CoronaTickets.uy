/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaEspectaculoDTO;
import DTOs.LoginDTO;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorUsuario;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pabli
 */
@Path("espectaculos")
public class Espectaculos {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaEspectaculo(AltaEspectaculoDTO espec) {
        //plataforma, nickname, nombre, descripcion, duracion, especMin, especMax, url, costo, "i", urlImagen, categorias
        //String nombrePlataforma, String nombreOrganizador, String nombreEspectaculo, String descripcion, Double duracion, int cantEspectadoresMinima, int cantEspectadoresMaxima, String URL, Double Costo, String estado, String imagen, String[] categorias
        if(ICE.altaEspectaculoWEB(espec.getPlataforma(),espec.getNickname(),espec.getNombreEspec(),espec.getDescripcion(),espec.getDuracion(),espec.getEspecMin(),espec.getEspecMax(),espec.getUrl(),espec.getCosto(),espec.getEstado(),espec.getImagen(),espec.getCategorias()) == true){
            return Response.ok().build();
            //return Response.ok(uDTO, MediaType.APPLICATION_JSON).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
