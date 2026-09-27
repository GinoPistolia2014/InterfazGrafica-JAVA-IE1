/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public interface Reproducible {
    
    default void intentarReproduccion(Ecosistema eco){
        
        if(puedeReproducirse()){
            reproducirse(eco);
        } else {
            System.out.println("No se puede reproducir en este momento");
        }
        
    }
    
    void reproducirse(Ecosistema eco);
    boolean puedeReproducirse();
}
