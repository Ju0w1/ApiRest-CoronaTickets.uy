package Services;

import DTOs.AltaFuncionDTO;
import DTOs.ArtistasDeFuncionDTO;
import DTOs.FuncionDTO;
import DTOs.TransporteListaEspectaculosAceptadosArtistaDTO;
import Logica.Clases.Artista;
import Logica.Clases.Espectaculo;
import Logica.Clases.Funcion;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorFuncion;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("funciones")
public class Funciones {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    IControladorFuncion ICF = fabrica.getIControladorFuncion();
    
    
    @GET
    @Path("/consulta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaFuncion (@QueryParam("funcion") String nomFuncion) {
        Funcion funcion = ICF.obtenerFuncion(nomFuncion);
        if (funcion==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else{
            FuncionDTO funDto= new FuncionDTO(funcion.getNombre(), funcion.getFecha(), funcion.getHoraInicio(), funcion.getFechaRegistro(), funcion.getUrlIamgen(), funcion.getEspectaculo().getNombre());
            
            return Response.ok(funDto, MediaType.APPLICATION_JSON).build();
        }
    }
    
    
    @GET
    @Path("/artistasDeFuncion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response artistasDeFuncion (@QueryParam("funcion") String nomFuncion) {
        try {
            List<String> artistasDeFuncion = ICF.artistasDeFuncion(nomFuncion);
            ArtistasDeFuncionDTO artistas = new ArtistasDeFuncionDTO(artistasDeFuncion);
            return Response.ok(artistas, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
    }
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaFuncion(AltaFuncionDTO funcion) {
        try {
            ICF.addFuncionWEBV2(funcion.getNombreEspectaculo(), funcion.getNombre(),funcion.getFechaRegistro(),funcion.getHoraInicio(),funcion.getFecha(), funcion.getArtistas(), funcion.getUrlImagen());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    @GET
    @Path("/espectaculosAceptados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response espectaculosAceptados (@QueryParam("artista") String nick) {
        try {
            Map<String, Espectaculo> espectaculos = ICE.obtenerEspectaculosAceptadosDeArtistaPorNick(nick);
            List<String> espectaculosAceptados = new ArrayList<>();
            
            espectaculos.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                espectaculosAceptados.add(value.getNombre());
            });
            
            TransporteListaEspectaculosAceptadosArtistaDTO especs = new TransporteListaEspectaculosAceptadosArtistaDTO(espectaculosAceptados);
            
            return Response.ok(especs, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    
}
