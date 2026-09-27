/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public interface Mortal {
    
    double obtenerEnergia();
    
    default void verificarMuerte(){
        double energia = obtenerEnergia();
        if(energia <= 0 && (!estaVivo())){
            morir();
            System.out.println("La entidad ha muerto");
        } else {
            System.out.println("Aún sigue con vida");
        }
    };
    
    public boolean estaVivo();
    public void morir();
    
}
