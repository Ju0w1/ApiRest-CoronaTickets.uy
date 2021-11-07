/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * @author diete
 */
public class PaqueteDTO implements Serializable {
    @NotNull
    private String Nombre;
    private boolean vigente;
    
    @Null
    private String Descripcion;
    private Date Fecha_Inicio;
    private Date Fecha_Fin;
    private Float Costo;
    private Float Descuento;
    private Date Fecha_Compra;
    private String url_imagen;
    private Date Fecha_Alta;
    
    //Getters
    public String getNombre(){
        return this.Nombre;
    }
    public String getDescripcion(){
        return this.Descripcion;
    }
    public Date getFecha_Inicio(){
        return this.Fecha_Inicio;
    }
    public Date getFecha_Fin(){
        return this.Fecha_Fin;
    }
    public Float getCosto(){
        return this.Costo;
    }
    public Float getDescuento(){
        return this.Descuento;
    }
    public String getUrl(){
        return this.url_imagen;
    }
    public Date getFechaAlta(){
        return this.Fecha_Alta;
    }
    public boolean getVigencia(){
        return this.vigente;
    }
    public Date getFecha_Compra(){
        return this.Fecha_Compra;
    }
    //Setters
    public void setNombre(String nombre){
        this.Nombre= nombre;
    }
    public void setDescripcion(String descripcion){
        this.Descripcion= descripcion;
    }
    public void setFecha_Inicio(Date fecha_inicio){
        this.Fecha_Inicio= fecha_inicio;
    }
    public void setFecha_Fin(Date fecha_fin){
        this.Fecha_Fin=fecha_fin;
    }
    public void setCosto(Float costo){
        this.Costo=costo;
    }
    public void setDescuento(Float descuento){
        this.Descuento=descuento;
    }
    public void setUrl(String url){
        this.url_imagen=url;
    }
    public void setFechaAlta(Date fechaAlta){
        this.Fecha_Alta=fechaAlta;
    }
    public void setVigencia(boolean vigente){
        this.vigente=vigente;
    }    
    public void setFecha_Compra(Date fecha_compra){
        this.Fecha_Compra= fecha_compra;
    }
    
}
