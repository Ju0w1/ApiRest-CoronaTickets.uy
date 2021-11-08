/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author Davila
 */
public class ArtistaDTO extends UserDTO{
    private String descripcion;
    private String biografia;
    private String linkWeb;
    
    //Getters
    public String getDescripcion() {
        return descripcion;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getLinkWeb() {
        return linkWeb;
    }
    
    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }
    
    
}
