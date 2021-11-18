/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AgregarEspectaculoAPaqueteDTO;
import DTOs.AltaEspectaculoDTO;
import DTOs.ConsultaEspectaculoDTO;
import DTOs.FuncionDTOConsultaEspectaculo;
import DTOs.HomeEspectaculoDTO;
import DTOs.HomePaqueteDTO;
import DTOs.LoginDTO;
import DTOs.TransporteListaEspectaculosHomeDTO;
import DTOs.TransporteListaPaquetesHomeDTO;
import Logica.Clases.Categoria;
import Logica.Clases.Espectaculo;
import Logica.Clases.Funcion;
import Logica.Clases.Paquete;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorPaquete;
import Logica.Interfaz.IControladorUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
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
@Path("espectaculos")
public class Espectaculos {

    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    IControladorPaquete ICP = fabrica.getIControladorPaquete();

    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaEspectaculo(AltaEspectaculoDTO espec) {
        //plataforma, nickname, nombre, descripcion, duracion, especMin, especMax, url, costo, "i", urlImagen, categorias
        //String nombrePlataforma, String nombreOrganizador, String nombreEspectaculo, String descripcion, Double duracion, int cantEspectadoresMinima, int cantEspectadoresMaxima, String URL, Double Costo, String estado, String imagen, String[] categorias
        if (ICE.altaEspectaculoWEB(espec.getPlataforma(), espec.getNickname(), espec.getNombreEspec(), espec.getDescripcion(), espec.getDuracion(), espec.getEspecMin(), espec.getEspecMax(), espec.getUrl(), espec.getCosto(), espec.getEstado(), espec.getImagen(), espec.getCategorias()) == true) {
            return Response.ok().build();
            //return Response.ok(uDTO, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspectaculoEspecífico(@QueryParam("nombre") String nombre) {
        try {

            Map<String, Espectaculo> escp = (Map<String, Espectaculo>) ICE.getEspectaculos();
            Espectaculo espcSeleccionado = escp.get(nombre);

            Map<String, Funcion> funcionesDeEspec = ICE.obtenerMapFunciones(nombre);
            Map<String, Paquete> paquetes = (Map<String, Paquete>) ICP.getPaqueteDeEspectaculo(nombre);
            List<Categoria> list = new ArrayList<Categoria>(espcSeleccionado.getCategorias().values());

            List<FuncionDTOConsultaEspectaculo> listFuncion = new ArrayList<>();
            for (Map.Entry<String, Funcion> entry : funcionesDeEspec.entrySet()) {
                FuncionDTOConsultaEspectaculo x = new FuncionDTOConsultaEspectaculo(entry.getValue().getNombre(), entry.getValue().getUrlIamgen());
                listFuncion.add(x);
            }
            List<Paquete> listPaquete = new ArrayList<Paquete>(paquetes.values());
            //ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getCosto(), espcSeleccionado.getDuracion(), espcSeleccionado.getFecha(),espcSeleccionado.getCategorias(), espcSeleccionado.getUrlIamgen(), espcSeleccionado.getPlataforma(), espcSeleccionado.getEstado());
            //ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getDuracion(), espcSeleccionado.getCosto(), espcSeleccionado.getFecha(), espcSeleccionado.getUrlIamgen());
            ConsultaEspectaculoDTO consultaespec = new ConsultaEspectaculoDTO(espcSeleccionado.getNombre(), espcSeleccionado.getArtista(), espcSeleccionado.getDescripcion(), espcSeleccionado.getMin(), espcSeleccionado.getMax(), espcSeleccionado.getUrl(), espcSeleccionado.getCosto(), espcSeleccionado.getDuracion(), espcSeleccionado.getFecha(), list, espcSeleccionado.getUrlIamgen(), listFuncion, listPaquete);
//            Gson gson = new Gson();

//            Gson gson = new Gson();
//            String json = gson.toJson(consultaespec);
            return Response.ok(consultaespec, MediaType.APPLICATION_JSON).build();
            //return Response.status(Status.OK).entity(consultaespec).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/obtenerEspectaculosDeArtistas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEspectaculosDeArtistas(@QueryParam("paquete") String nomPaquete, @QueryParam("plataforma") String nomPlataform, @QueryParam("nickname") String nickname) {
        try {
            ArrayList<String> espectaculos = (ArrayList<String>) ICE.obtenerEspectaculosDeArtistaQueNoEstanEnPaquete(nomPaquete, nomPlataform, nickname);
            AgregarEspectaculoAPaqueteDTO especs = new AgregarEspectaculoAPaqueteDTO(espectaculos);
            return Response.ok(especs, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/altaEspectaculoAPaquete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaEspectaculoAPaquete(AgregarEspectaculoAPaqueteDTO espec) {
        try {
            ICP.AgregarEspPaq(espec.getEspectaculo(), espec.getPaquete());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    
    @GET
    @Path("/listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEspectaculos() {
        Map<String, Espectaculo> espectaculos = (Map<String, Espectaculo>) ICE.getEspectaculos();
        
        List<HomeEspectaculoDTO> listEspectaculos = new ArrayList<>();
        
        espectaculos.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
             listEspectaculos.add(new HomeEspectaculoDTO(value.getNombre(), value.getUrlIamgen()));
             System.out.println(value.getNombre());
        });
        
        if(listEspectaculos.isEmpty()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }else{
            TransporteListaEspectaculosHomeDTO paquetesDTO = new TransporteListaEspectaculosHomeDTO(listEspectaculos);
            return Response.ok(paquetesDTO, MediaType.APPLICATION_JSON).build();
        }
    }
}
