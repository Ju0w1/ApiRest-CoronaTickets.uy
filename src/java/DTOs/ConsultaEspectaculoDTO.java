/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import Logica.Clases.Categoria;
import Logica.Clases.Funcion;
import Logica.Clases.Paquete;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * @author pabli
 */
public class ConsultaEspectaculoDTO implements Serializable {
    private String Nombre;
    private int Artista;
    private String Descripcion;
    private int Cant_min_espectadores;
    private int Cant_max_espectadores;
    private String Url;
    private double Costo;
    private double Duracion;
    private Date Fecha_Registro;
    private Map<String, Categoria> categorias;

    private String url_imagen;
    private String plataforma;
    private String estado;
    private Map<String, Funcion> funciones;
    private Map<String, Paquete> paquetes;
    
    public ConsultaEspectaculoDTO(){
        
    }

    public ConsultaEspectaculoDTO(String Nombre, int Artista, String Descripcion, int Cant_min_espectadores, int Cant_max_espectadores, String Url, double Costo, double Duracion, Date Fecha_Registro, Map<String, Categoria> categorias, String url_imagen, String plataforma, String estado, Map<String, Funcion> funciones, Map<String, Paquete> paquetes) {
        this.Nombre = Nombre;
        this.Artista = Artista;
        this.Descripcion = Descripcion;
        this.Cant_min_espectadores = Cant_min_espectadores;
        this.Cant_max_espectadores = Cant_max_espectadores;
        this.Url = Url;
        this.Costo = Costo;
        this.Duracion = Duracion;
        this.Fecha_Registro = Fecha_Registro;
        this.categorias = categorias;
        this.url_imagen = url_imagen;
        this.plataforma = plataforma;
        this.estado = estado;
        this.funciones = funciones;
        this.paquetes = paquetes;
    }

    public ConsultaEspectaculoDTO(String Nombre, int Artista, String Descripcion, int Cant_min_espectadores, int Cant_max_espectadores, String Url, double Costo, double Duracion, Date Fecha_Registro, Map<String, Categoria> categorias, String url_imagen, Map<String, Funcion> funciones, Map<String, Paquete> paquetes) {
        this.Nombre = Nombre;
        this.Artista = Artista;
        this.Descripcion = Descripcion;
        this.Cant_min_espectadores = Cant_min_espectadores;
        this.Cant_max_espectadores = Cant_max_espectadores;
        this.Url = Url;
        this.Costo = Costo;
        this.Duracion = Duracion;
        this.Fecha_Registro = Fecha_Registro;
        this.categorias = categorias;
        this.url_imagen = url_imagen;
        this.funciones = funciones;
        this.paquetes = paquetes;
    }
    
    public ConsultaEspectaculoDTO(String Nombre, int Artista, String Descripcion, int Cant_min_espectadores, int Cant_max_espectadores, String Url, double Costo, double Duracion, Date Fecha_Registro, String url_imagen) {
        this.Nombre = Nombre;
        this.Artista = Artista;
        this.Descripcion = Descripcion;
        this.Cant_min_espectadores = Cant_min_espectadores;
        this.Cant_max_espectadores = Cant_max_espectadores;
        this.Url = Url;
        this.Costo = Costo;
        this.Duracion = Duracion;
        this.Fecha_Registro = Fecha_Registro;
        this.url_imagen = url_imagen;

    }
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setArtista(int Artista) {
        this.Artista = Artista;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setCant_min_espectadores(int Cant_min_espectadores) {
        this.Cant_min_espectadores = Cant_min_espectadores;
    }

    public void setCant_max_espectadores(int Cant_max_espectadores) {
        this.Cant_max_espectadores = Cant_max_espectadores;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setCosto(double Costo) {
        this.Costo = Costo;
    }

    public void setDuracion(double Duracion) {
        this.Duracion = Duracion;
    }

    public void setFecha_Registro(Date Fecha_Registro) {
        this.Fecha_Registro = Fecha_Registro;
    }

    public void setCategorias(Map<String, Categoria> categorias) {
        this.categorias = categorias;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFunciones(Map<String, Funcion> funciones) {
        this.funciones = funciones;
    }

    public void setPaquetes(Map<String, Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getArtista() {
        return Artista;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public int getCant_min_espectadores() {
        return Cant_min_espectadores;
    }

    public int getCant_max_espectadores() {
        return Cant_max_espectadores;
    }

    public String getUrl() {
        return Url;
    }

    public double getCosto() {
        return Costo;
    }

    public double getDuracion() {
        return Duracion;
    }

    public Date getFecha_Registro() {
        return Fecha_Registro;
    }

    public Map<String, Categoria> getCategorias() {
        return categorias;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getEstado() {
        return estado;
    }

    public Map<String, Funcion> getFunciones() {
        return funciones;
    }

    public Map<String, Paquete> getPaquetes() {
        return paquetes;
    }
    
    

}
