/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaEspectaculoDTO;
import DTOs.CanjearFuncionesDTO;
import DTOs.PaqueteRegistroDTO;
import DTOs.RegistroFuncionDTO;
import DTOs.RegistrosPreviosDTO;
import DTOs.TransportadorListaRegistrosPreviosDTO;
import DTOs.TransporteListaPaquetesRegistroDTO;
import Logica.Clases.Paquete;
import Logica.Clases.Registro;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
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

/**
 *
 * @author pabli
 */
@Path("registros")
public class Registros {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    
    @GET
    @Path("/registrosPrevios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegistrosPrevios(@QueryParam("user") String user) {
        Map<String,Registro> registrosPrevios = ICE.obtenerRegistrosPreviosDeEspectador(user);
        if(registrosPrevios.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else{
            if(registrosPrevios.size()>=3){
                List<RegistrosPreviosDTO> regPrev = new ArrayList<>();
                registrosPrevios.entrySet().forEach((entry) -> {
                    Registro value = entry.getValue();
                    regPrev.add(new RegistrosPreviosDTO(value.getFuncion(),value.getFecha(),value.getCanjeado()));
                });
                TransportadorListaRegistrosPreviosDTO lista = new TransportadorListaRegistrosPreviosDTO(regPrev);
                return Response.ok(lista, MediaType.APPLICATION_JSON).build();
            }else{
                return Response.status(Response.Status.NO_CONTENT).build();  
            }
        }
    }
    
    @GET
    @Path("/paquetesSinCanjear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPaquetesSinCanjear(@QueryParam("user") String user,@QueryParam("funcion") String funcion) {
        Map<String, Paquete> paquetes = ICE.obtenerPaquetesSinCanjear(user, funcion);
        if(paquetes.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else{
            List<PaqueteRegistroDTO> paquetesDTOs = new ArrayList<>();
            
            paquetes.entrySet().forEach((entry) -> {
                Paquete value = entry.getValue();
                paquetesDTOs.add(new PaqueteRegistroDTO(value.getNombre(),value.getDescripcion(),value.getUrl()));
            });
            TransporteListaPaquetesRegistroDTO lista = new TransporteListaPaquetesRegistroDTO(paquetesDTOs);
            return Response.ok(lista, MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/registro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPaquetesSinCanjear(RegistroFuncionDTO reg) {
        try {
            ICE.registroFuncionWEB(reg.getFuncion(),reg.getUser());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    @Path("/canjearFunciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response canjearFunciones(CanjearFuncionesDTO canje) {
        try {
            ICE.actualizarEstadoDeRegistrosWEB(canje.getFuncionAAgregar(), canje.getFuncion1(), canje.getFuncion2(), canje.getFuncion3(), canje.getUser());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
