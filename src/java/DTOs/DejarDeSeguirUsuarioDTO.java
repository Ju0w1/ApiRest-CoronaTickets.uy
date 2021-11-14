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
public class DejarDeSeguirUsuarioDTO {
    private String nickADejar;
    private String nickseguidor;

    public DejarDeSeguirUsuarioDTO(String nickADejar, String nickseguidor) {
        this.nickADejar = nickADejar;
        this.nickseguidor = nickseguidor;
    }

    public String getNickADejar() {
        return nickADejar;
    }

    public String getNickseguidor() {
        return nickseguidor;
    }

    public void setNickADejar(String nickADejar) {
        this.nickADejar = nickADejar;
    }

    public void setNickseguidor(String nickseguidor) {
        this.nickseguidor = nickseguidor;
    }
    
    
    
}
