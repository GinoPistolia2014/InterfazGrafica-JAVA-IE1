/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie1.interfazgrafica;

public interface Mortal {

    double obtenerEnergia();

    String obtenerNombre();

    default void verificarMuerte() {
        if (obtenerEnergia() <= 0 && estaVivo()) {
            morir();
            System.out.println(
                    obtenerNombre()
                    + " murio por falta de energia.");
        }
    }

    boolean estaVivo();

    void morir();
}