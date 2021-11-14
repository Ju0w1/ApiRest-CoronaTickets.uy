/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.BooleanDTO;
import DTOs.FuncionesDeUserDTO;
import DTOs.ListFuncionesDeUserDTO;
import DTOs.ListPaquetesDeUserDTO;
import DTOs.ListUserEspectDTO;
import DTOs.PaquetesDeUserDTO;
import DTOs.UserDTO;
import DTOs.UserEspectDTO;
import DTOs.followDTO;
import Logica.Clases.Artista;
import Logica.Clases.Espectaculo;
import Logica.Clases.Funcion;
import Logica.Clases.Paquete;
import Logica.Clases.Usuario;
import Logica.DataTypes.DTFecha;
import Logica.Fabrica;
import Logica.Interfaz.IControladorUsuario;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
@Path("usuarios")
public class Usuarios {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();

    @GET
    @Path("/")
    public Response getUsers(@QueryParam("nickname") String nickname) {
        try {

            Usuario usuario = ICU.obtenerEspectadorPorNick(nickname);
            System.out.println("Services.Users.getUsers()" + usuario.getNombre());
            UserDTO uDTO = new UserDTO();
            uDTO.setEmail(usuario.getEmail());
            uDTO.setNombre(usuario.getNombre());

            return Response.ok(uDTO, MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            System.out.println("Services.Users.getUsers()" + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDTO user) {
        return Response.ok("Welcomee to the jungle " + user.getNombre()).build();
    }
    
    @POST
    @Path("/edit") // Recibe un UserDTO y actualiza los datos en la Base : MILTON
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(UserDTO user) throws ParseException {
        //return Response.ok("Welcomee to the jungle " + user.getNombre()).build();

        if(ICU.obtenerArtistaPorNick(user.getNickname())!=null){
            Artista art = ICU.obtenerArtistaPorNick(user.getNickname());
            String fecha = (art.getNacimiento().getAnio() + "-" + art.getNacimiento().getMes() + "-" + art.getNacimiento().getDia());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento= new Date();
            System.out.println("Fecha de tipo DATE::" + fechaNacimiento);
            fechaNacimiento = format.parse(fecha);
            UserDTO artDTO = new UserDTO(art.getNickname(), art.getNombre(), art.getApellido(), art.getEmail(), fechaNacimiento, art.getImagen(), art.getDescripcion(), art.getBiografia(), art.getLinkWeb(), "artista",0,0);
            
            System.out.println("Fecha:: " + fecha);
            return Response.ok(artDTO, MediaType.APPLICATION_JSON).build();
        } else {
            Usuario espec = ICU.obtenerEspectadorPorNick(user.getNickname());
            String fecha = (espec.getNacimiento().getAnio() + "-" + espec.getNacimiento().getMes() + "-" + espec.getNacimiento().getDia());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento= new Date();
            fechaNacimiento = format.parse(fecha);
            //UserDTO(String nickname, String nombre, String apellido, String email, Date nacimiento)
            UserDTO especDTO = new UserDTO(espec.getNickname(), espec.getNombre(), espec.getApellido(), espec.getEmail(), fechaNacimiento, espec.getImagen(), "espectador",0,0);
            return Response.ok(especDTO, MediaType.APPLICATION_JSON).build();
        }
        
    }
    @POST
    @Path("/updateUser") // Recibe un UserDTO y actualiza los datos en la Base : MILTON
    @Produces(MediaType.APPLICATION_JSON)
    public Response UserUpdated(UserDTO user) throws ParseException {
        if (ICU.obtenerArtistaPorNick(user.getNickname())==null){ // ESPECTADOR
            Usuario espec = new Usuario();
            String nuevaImagen = user.getUrl_imagen();
            if(user.getUrl_imagen().equals("")){
                espec = ICU.obtenerEspectadorPorNick(user.getNickname());
                nuevaImagen = espec.getImagen();
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
            Date date = new Date();
            date = user.getNacimiento();
            String strDate = dateFormat.format(date);
            java.sql.Date dateSqlTest = java.sql.Date.valueOf(strDate);
            ICU.modificarUsuarioEspectador(user.getNickname(), user.getEmail(), user.getNombre(), user.getApellido(), dateSqlTest, nuevaImagen);
            UserDTO especDTO = new UserDTO(espec.getNickname(),nuevaImagen);
            return Response.ok(especDTO, MediaType.APPLICATION_JSON).build();
        } else { //ARTISTA
            Artista art = new Artista();
            String nuevaImagen = user.getUrl_imagen();
            if(user.getUrl_imagen().equals("")){
                art = ICU.obtenerArtistaPorNick(user.getNickname());
                nuevaImagen = art.getImagen();
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
            Date date = new Date();
            date = user.getNacimiento();
            String strDate = dateFormat.format(date);
            java.sql.Date dateSqlTest = java.sql.Date.valueOf(strDate);
            ICU.modificarUsuarioArtista(user.getNickname(), user.getEmail(), user.getNombre(), user.getApellido(), dateSqlTest, nuevaImagen, user.getDesc(), user.getBio(), user.getLink());
            UserDTO artDTO = new UserDTO(art.getNickname(),nuevaImagen);
            return Response.ok(artDTO, MediaType.APPLICATION_JSON).build();
        }
    }
    @POST
    @Path("/loadUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response LoadUser(UserDTO user) throws ParseException {
        int IDUser = ICU.getIdEspectadorPorNick(user.getNickname());
        int seguidores = ICU.getSeguidores(IDUser);
        int seguidos = ICU.getSeguidos(IDUser);
        if(ICU.obtenerArtistaPorNick(user.getNickname()) == null){ //ESPECTADOR
            Usuario espec = ICU.obtenerEspectadorPorNick(user.getNickname());
            String fecha = (espec.getNacimiento().getAnio() + "-" + espec.getNacimiento().getMes() + "-" + espec.getNacimiento().getDia());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento= new Date();
            fechaNacimiento = format.parse(fecha);
            UserDTO especDTO = new UserDTO(espec.getNickname(), espec.getNombre(), espec.getApellido(), espec.getEmail(), fechaNacimiento, espec.getImagen(), "espectador", seguidores, seguidos);
            return Response.ok(especDTO, MediaType.APPLICATION_JSON).build();
        } else { //ARTISTA
            Artista art = ICU.obtenerArtistaPorNick(user.getNickname());
            String fecha = (art.getNacimiento().getAnio() + "-" + art.getNacimiento().getMes() + "-" + art.getNacimiento().getDia());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento= new Date();
            fechaNacimiento = format.parse(fecha);
            UserDTO artDTO = new UserDTO(art.getNickname(), art.getNombre(), art.getApellido(), art.getEmail(), fechaNacimiento, art.getImagen(), art.getDescripcion(), art.getBiografia(), art.getLinkWeb(), "artista", seguidores, seguidos);
            return Response.ok(artDTO, MediaType.APPLICATION_JSON).build();
        }
    }
    @POST
    @Path("/mapsUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMapsDeUser(UserDTO user) throws ParseException {
        //if (user.getTipo()!= null){ // SI No tiene un tipo setiado es porque es la primer llamada y quiere las funciones
            Map<String, Funcion> funciones = Fabrica.getInstance().getIControladorEspectaculo().getRegistroDeFuncionesDeUsuarioPorNick(user.getNickname());
            List<FuncionesDeUserDTO> ListFunciones = new ArrayList<>();
            for (Map.Entry<String, Funcion> entry : funciones.entrySet()) {
                String key = entry.getKey();
                Funcion value = entry.getValue();
                ListFunciones.add(new FuncionesDeUserDTO(value.getNombre(),value.getEspectaculo().getNombre(), value.getEspectaculo().getPlataforma()));
            }
            ListFuncionesDeUserDTO funcionesX= new ListFuncionesDeUserDTO(ListFunciones);

            return Response.ok(funcionesX, MediaType.APPLICATION_JSON).build();
       // } else {
//            int idEspectador = ICU.getIdEspectadorPorNick(user.getNickname());
//            Map<String, Paquete> paquetesRegistrado = Fabrica.getInstance().getIControladorPaquete().getPaquetesQueComproUsuario(idEspectador);
//            List<PaquetesDeUserDTO> ListPaquetes = new ArrayList<>();
//            for(Map.Entry<String, Paquete> entry : paquetesRegistrado.entrySet()){
//                String key = entry.getKey();
//                Paquete value = entry.getValue();
//                ListPaquetes.add(new PaquetesDeUserDTO(value.getNombre(), value.getDescuento().toString()));
//            }
//            ListPaquetesDeUserDTO paquetesX = new ListPaquetesDeUserDTO(ListPaquetes);
//            return Response.ok(paquetesX, MediaType.APPLICATION_JSON).build();
        //}
    }
    @POST
    @Path("/paquetesUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPaquetesDeUser(UserDTO user) throws ParseException {
        int idEspectador = ICU.getIdEspectadorPorNick(user.getNickname());
        Map<String, Paquete> paquetesRegistrado = Fabrica.getInstance().getIControladorPaquete().getPaquetesQueComproUsuario(idEspectador);
        List<PaquetesDeUserDTO> ListPaquetes = new ArrayList<>();
        for(Map.Entry<String, Paquete> entry : paquetesRegistrado.entrySet()){
            String key = entry.getKey();
            Paquete value = entry.getValue();
            ListPaquetes.add(new PaquetesDeUserDTO(value.getNombre(), value.getDescuento().toString()));
        }
        ListPaquetesDeUserDTO paquetesX = new ListPaquetesDeUserDTO(ListPaquetes);
        return Response.ok(paquetesX, MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Path("/espectAceptados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspectaculosAceptados(UserDTO user) throws ParseException {
        Map<String, Espectaculo> espectaculosA = Fabrica.getInstance().getIControladorEspectaculo().obtenerEspectaculosAceptadosDeArtistaPorNick(user.getNickname());      
        
        List<UserEspectDTO> ListEspectaculos = new ArrayList<>();
        for(Map.Entry<String, Espectaculo> entry : espectaculosA.entrySet()){
            String key = entry.getKey();
            Espectaculo value = entry.getValue();
            ListEspectaculos.add(new UserEspectDTO(value.getNombre(), value.getPlataforma(), String.valueOf(value.getCosto())));
        }
        ListUserEspectDTO espectaculos = new ListUserEspectDTO(ListEspectaculos);
        
        return Response.ok(espectaculos, MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Path("/espectIngresados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspectaculosIngresados(UserDTO user) throws ParseException {
        Map<String, Espectaculo> espectaculosI = Fabrica.getInstance().getIControladorEspectaculo().obtenerEspectaculosIngresadosDeArtistaPorNick(user.getNickname());      
        
        List<UserEspectDTO> ListEspectaculos = new ArrayList<>();
        for(Map.Entry<String, Espectaculo> entry : espectaculosI.entrySet()){
            String key = entry.getKey();
            Espectaculo value = entry.getValue();
            ListEspectaculos.add(new UserEspectDTO(value.getNombre(), value.getPlataforma(), String.valueOf(value.getCosto())));
        }
        ListUserEspectDTO espectaculos = new ListUserEspectDTO(ListEspectaculos);
        
        return Response.ok(espectaculos, MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Path("/espectRechazados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEspectaculosRechazados(UserDTO user) throws ParseException {
        Map<String, Espectaculo> espectaculosR = Fabrica.getInstance().getIControladorEspectaculo().obtenerEspectaculosRechazadosDeArtistaPorNick(user.getNickname());      
        
        List<UserEspectDTO> ListEspectaculos = new ArrayList<>();
        for(Map.Entry<String, Espectaculo> entry : espectaculosR.entrySet()){
            String key = entry.getKey();
            Espectaculo value = entry.getValue();
            ListEspectaculos.add(new UserEspectDTO(value.getNombre(), value.getPlataforma(), String.valueOf(value.getCosto())));
        }
        ListUserEspectDTO espectaculos = new ListUserEspectDTO(ListEspectaculos);
        
        return Response.ok(espectaculos, MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Path("/Losigo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response losigo(followDTO datos) throws ParseException {
        Boolean losigo;
        if(ICU.loSigo(datos.getYo(), datos.getEl())){
            losigo = true;
            System.out.println("SI LO SIGO");
        } else {
            System.out.println("NO LO SIGO");
            losigo = false;
        }
        BooleanDTO respuesta = new BooleanDTO(losigo);
        return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
    }
    
}



