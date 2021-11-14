/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DTOs.AltaPaqueteDTO;
import DTOs.CompraPaqueteDTO;
import DTOs.ConsultaPaqueteDTO;
import DTOs.EspectaculoPaqueteDTO;
import DTOs.TransporteListEspectaculosDePaqueteDTO;
import Logica.Clases.Espectaculo;
import Logica.Clases.Paquete;
import Logica.DataTypes.DTFecha;
import Logica.Fabrica;
import Logica.Interfaz.IControladorEspectaculo;
import Logica.Interfaz.IControladorUsuario;
import Logica.Interfaz.IControladorPaquete;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("paquetes")
public class Paquetes {
    Fabrica fabrica = Fabrica.getInstance();
    IControladorUsuario ICU = fabrica.getIControladorUsuario();
    IControladorPaquete ICP = fabrica.getIControladorPaquete();
    IControladorEspectaculo ICE = fabrica.getIControladorEspectaculo();
    
    @POST
    @Path("/alta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response altaPaquete(AltaPaqueteDTO paq) {
        if (ICP.isNombrePaqueteUsado(paq.getNombre()) == true) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }else{
            ICP.altaPaquete(paq.getNombre(),paq.getFechaInicio(),paq.getFechaFin(),paq.getFechaCreado(),paq.getDescuento(),paq.getDescipcion(),paq.getImagen());
            return Response.ok().build();
        }
    }
    
    @GET
    @Path("/consultaEspectaculos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaEspectaculosDePaquete(@QueryParam("paquete") String nomPaquete) {
        
        try {
            Map<String,Espectaculo> espectaculos = ICE.obtenerMapEspectaculosDePaquete(nomPaquete);
            List<EspectaculoPaqueteDTO> especs = new ArrayList<>();

            espectaculos.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((value) -> {
                especs.add(new EspectaculoPaqueteDTO(value.getNombre(),value.getDescripcion(),String.valueOf(value.getCosto()),value.getUrlIamgen()));
            });
            
            TransporteListEspectaculosDePaqueteDTO dto = new TransporteListEspectaculosDePaqueteDTO(especs);
            
            return Response.ok(dto, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
            
        }

        
    }
    
    @GET
    @Path("/consultaPaq")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPaquete(@QueryParam("paquete") String nomPaquete) {
        System.out.println("Entró:"+nomPaquete);
        Paquete paquete = ICP.obtenerPaquetePorNombre(nomPaquete);
        String nombrePaq = paquete.getNombre();
        String descripcion = paquete.getDescripcion();
        float descuento =  paquete.getDescuento();
        String decuentoToString = String.valueOf(descuento);
        String imagen = paquete.getUrl();
        DTFecha fechaInicio = paquete.getFecha_Inicio();
        DTFecha fechaFin = paquete.getFecha_Fin();
        
        String mesInicio;
        if (fechaInicio.getMes() < 10) {
            mesInicio = "0" + fechaInicio.getMes();
        } else {
            mesInicio = Integer.toString(fechaInicio.getMes());
        }
        String diaInicio;
        if (fechaInicio.getDia() < 10) {
            diaInicio = "0" + fechaInicio.getDia();
        } else {
            diaInicio = Integer.toString(fechaInicio.getDia());
        }
        
        String mesFin;
        if (fechaFin.getMes() < 10) {
            mesFin = "0" + fechaFin.getMes();
        } else {
            mesFin = Integer.toString(fechaFin.getMes());
        }
        String diaFin;
        if (fechaFin.getDia() < 10) {
            diaFin = "0" + fechaFin.getDia();
        } else {
            diaFin = Integer.toString(fechaFin.getDia());
        }
        String fechaInicioString = fechaInicio.getAnio()+ "-" +mesInicio+ "-" +diaInicio;
        String fechaFinString = fechaFin.getAnio()+ "-" +mesFin + "-" + diaFin;

        
        if(paquete==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }else{
            ConsultaPaqueteDTO paqueteDTO = new ConsultaPaqueteDTO(nombrePaq, descripcion, fechaInicioString, fechaFinString, decuentoToString, imagen);
            System.out.println("NOMBRE OBTENIDO EN API:"+ paqueteDTO.getFechaFin());
            System.out.println("NOMBRE OBTENIDO EN API:"+ paqueteDTO.getFechaInicio());
            return Response.ok(paqueteDTO, MediaType.APPLICATION_JSON).build();
        }
        
    }
    
    @POST
    @Path("/compra")
    @Produces(MediaType.APPLICATION_JSON)
    public Response compraPaquete(CompraPaqueteDTO compra) {
        String nickUsuario = compra.getNick();
        String nomPaquete = compra.getPaquete();
        ICP.compraPaquete(nickUsuario, nomPaquete);
        return Response.ok("ok", MediaType.APPLICATION_JSON).build();
    }
}
