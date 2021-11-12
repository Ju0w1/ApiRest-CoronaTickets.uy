/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.ConsultaEspectaculoDTO;
import DTOs.EspectaculoDTO;
import DTOs.UserDTO;
import Logica.Clases.Categoria;
import Logica.Clases.Espectaculo;
import Logica.Clases.Funcion;
import Logica.Clases.Paquete;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorPaquete;
import Logica.Interfaz.IControladorUsuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author pabli
 */
@Path("espectaculos")
public class Espectaculos {

    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    IControladorPaquete ICP = fabrica.getIControladorPaquete();

    @GET
    @Path("/")
    public Response getEspectaculoEspecífico(@QueryParam("nombre") String nombre) {
        try {

            Map<String, Espectaculo> escp = (Map<String, Espectaculo>) ICE.getEspectaculos();
            Espectaculo espcSeleccionado = escp.get(nombre);

            Map<String, Funcion> funcionesDeEspec = ICE.obtenerMapFunciones(nombre);
            Map<String, Paquete> paquetes = (Map<String, Paquete>) ICP.getPaqueteDeEspectaculo(nombre);

            ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getCosto(), espcSeleccionado.getDuracion(), espcSeleccionado.getFecha(), espcSeleccionado.getCategorias(), espcSeleccionado.getUrlIamgen(), espcSeleccionado.getPlataforma(), espcSeleccionado.getEstado(), funcionesDeEspec, paquetes);
            System.out.println(consultaespec.getArtista());
            System.out.println(consultaespec.getCant_max_espectadores());
            System.out.println(consultaespec.getCant_min_espectadores());
            System.out.println(consultaespec.getCosto());
            System.out.println(consultaespec.getDescripcion());
            System.out.println(consultaespec.getDuracion());
            System.out.println(consultaespec.getFecha_Registro());
            System.out.println(consultaespec.getUrl());
            System.out.println(consultaespec.getUrl_imagen());
            Gson gson = new Gson();
            String json = gson.toJson(consultaespec);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDTO user) {
        return Response.ok("Welcomee to the jungle " + user.getNombre()).build();
    }
}
