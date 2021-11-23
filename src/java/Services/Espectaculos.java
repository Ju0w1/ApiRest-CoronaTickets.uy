/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AgregarEspectaculoAPaqueteDTO;
import DTOs.AltaEspectaculoDTO;
import DTOs.ConsultaEspectaculoDTO;
import DTOs.EspecFinalizadoDTO;
import DTOs.FavoritoDTO;
import DTOs.FuncionDTOConsultaEspectaculo;
import DTOs.HomeEspectaculoDTO;
import DTOs.HomePaqueteDTO;
import DTOs.LoginDTO;
import DTOs.TransporteListaEspecFinalizadosDTO;
import DTOs.TransporteListaEspectaculosHomeDTO;
import DTOs.TransporteListaNombresEspectaculosAceptadosDTO;
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
import DTOs.DTOListEspec;
import DTOs.DTOespec;
import DTOs.EspectDTO;
import DTOs.FuncionDTO;
import DTOs.CategoriaDTO;
import DTOs.ListEspectDTO;
import DTOs.ListFuncionDTO;
import DTOs.getFavoritosDTO;
import DTOs.yaEsFavoritoDTO;
import java.util.HashMap;

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
    public Response getEspectaculoEspecifico(@QueryParam("nombre") String nombre) { 
        try {

            Map<String, Espectaculo> escp = (Map<String, Espectaculo>) ICE.getEspectaculos();
            Espectaculo espcSeleccionado = escp.get(nombre);
            //System.out.println("EspecSelec: "+espcSeleccionado.getNombre());
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
    @Path("/finalizados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspectaculoFinalizados(@QueryParam("nombre") String nombre) {
        try {

            Map<String, Espectaculo> escp = (Map<String, Espectaculo>) ICE.getEspectaculosFinalizados();
            Espectaculo espcSeleccionado = escp.get(nombre);
            //System.out.println("EspecSelec: "+espcSeleccionado.getNombre());
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
    
    @GET
    @Path("/finalizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarEspectaculo(@QueryParam("espectaculo") String nomEspec) {
        
        if(ICE.FinalizarEspec(nomEspec)==true){
            return Response.ok().build();            
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("/espectaculosFinalizados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEspectaculosFinalizadosArtista(@QueryParam("artista") String nickname) {
        
        try {
            Map<String, Espectaculo> especs = ICE.obtenerEspectaculosFinalizadosDeArtistaPorNick(nickname);
            List<EspecFinalizadoDTO> especsFinalizados = new ArrayList<>();
            especs.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                especsFinalizados.add(new EspecFinalizadoDTO(value.getNombre(),value.getDescripcion(), value.getUrlIamgen()));
            });
            TransporteListaEspecFinalizadosDTO trEspecs = new TransporteListaEspecFinalizadosDTO(especsFinalizados);
            return Response.ok(trEspecs, MediaType.APPLICATION_JSON).build();            
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("/espectaculosDeArtista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEspectaculosDeArtista(@QueryParam("artista") String nickname) {
        
        try {
            Map<String, Espectaculo> especs = ICE.obtenerEspectaculosAceptadosDeArtistaPorNick(nickname);
            List<String> espectaculosActeptados = new ArrayList<>();
            especs.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                espectaculosActeptados.add(value.getNombre());
            });
            
            TransporteListaNombresEspectaculosAceptadosDTO trEspecs = new TransporteListaNombresEspectaculosAceptadosDTO(espectaculosActeptados);

            return Response.ok(trEspecs, MediaType.APPLICATION_JSON).build();            
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    @Path("/getFunciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFunciones(EspectDTO espec) {
        
        Map<String, Funcion> funcionesDeEspec = ICE.obtenerMapFunciones(espec.getNombre());
        
        List<FuncionDTO> funcionesList = new ArrayList<>();
        
        for (Map.Entry<String, Funcion> entry : funcionesDeEspec.entrySet()){
            Funcion value = entry.getValue();
            funcionesList.add(new FuncionDTO(value.getNombre(),value.getFecha(), value.getUrlIamgen()));
        }
        for(FuncionDTO fun : funcionesList){
            if(fun.getUrlImagen().equals("")){
                fun.setUrlImagen("https://i.imgur.com/Hh3cYL8.jpeg");
            }
        }
        ListFuncionDTO listaFunciones = new ListFuncionDTO(funcionesList);
        
        return Response.ok(listaFunciones, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/ObtenerEspectaculos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ObtenerEspectaculos() {
        Map<String, Espectaculo> espectaculos = (Map<String, Espectaculo>) ICE.getEspectaculos();
        List<DTOespec> ListEspectaculosDTO = new ArrayList<>();
        espectaculos.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
            
            List<CategoriaDTO> categoriasDTOList = new ArrayList<>();
            Map<String, Categoria> categoriasAux = value.getCategorias();
            for (Map.Entry<String, Categoria> entry2 : categoriasAux.entrySet()){
                Categoria cat = entry2.getValue();
                CategoriaDTO categoriaAuxDTO = new CategoriaDTO(cat.getNombre());
                categoriasDTOList.add(categoriaAuxDTO);
            }            
            
            ListEspectaculosDTO.add(new DTOespec(value.getNombre(), value.getPlataforma(), value.getUrlIamgen(), categoriasDTOList));
        });
        DTOListEspec espectaculosList = new DTOListEspec(ListEspectaculosDTO);
        return Response.ok(espectaculosList, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/marcarFavorito")
    @Produces(MediaType.APPLICATION_JSON)
    public Response marcarFavorito(FavoritoDTO addFav) {
        try {
            ICE.marcarEspectaculoFavorito(addFav.getUserNickname(),addFav.getEspecNombre());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("/getFavoritos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavoritos(@QueryParam("nombre") String nickname) {
        try {
            int favoritos = ICE.getFavoritos(nickname);
            getFavoritosDTO favDTO = new getFavoritosDTO();
            favDTO.setCantidadFavoritos(favoritos);
            return Response.ok(favDTO, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("/yaEsFavorito")
    @Produces(MediaType.APPLICATION_JSON)
    public Response yaEsFavorito(@QueryParam("nickname") String nickname, @QueryParam("nombre") String nombre) {
        try {
            boolean esFav = ICE.yaEsFavorito(nickname,nombre);
            yaEsFavoritoDTO yaEsFavDTO = new yaEsFavoritoDTO();
            yaEsFavDTO.setEsFavorito(esFav);
            return Response.ok(yaEsFavDTO, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
