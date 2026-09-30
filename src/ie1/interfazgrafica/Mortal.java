/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie1.interfazgrafica;

public interface Mortal {

    double obtenerEnergia();

    String obtenerNombre();

    /**
     * Si la entidad esta viva pero se quedo sin energia, la mata.
     * Devuelve true cuando la entidad murio en esta verificacion, para que
     * el Ecosistema registre el evento y lo imprima con los eventos del turno.
     */
    default boolean verificarMuerte() {
        if (obtenerEnergia() <= 0 && estaVivo()) {
            morir();
            return true;
        }

        return false;
    }

    boolean estaVivo();

    void morir();
}