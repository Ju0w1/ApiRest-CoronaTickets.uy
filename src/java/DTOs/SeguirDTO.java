/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.Serializable;

/**
 *
 * @author pabli
 */
public class SeguirDTO implements Serializable{
    private String nickASeguir;
    private String nickSeguidor;

    public SeguirDTO() {
    }

    public SeguirDTO(String nickASeguir, String nickSeguidor) {
        this.nickASeguir = nickASeguir;
        this.nickSeguidor = nickSeguidor;
    }

    public String getNickASeguir() {
        return nickASeguir;
    }

    public String getNickSeguidor() {
        return nickSeguidor;
    }

    public void setNickASeguir(String nickASeguir) {
        this.nickASeguir = nickASeguir;
    }

    public void setNickSeguidor(String nickSeguidor) {
        this.nickSeguidor = nickSeguidor;
    }
    
    
}
