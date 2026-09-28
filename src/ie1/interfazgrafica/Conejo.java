/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public class Conejo extends Animal implements Reproducible {
    
    public Conejo(String nombre, double energia, int velocidad, double peso){
        super(nombre, energia, velocidad, peso);
    }
    
    @Override
    protected void actuar(Ecosistema eco){
        comer(eco);
        intentarReproduccion(eco);
    }
    
    @Override
    void comer(Ecosistema eco){
        ////CODE GOES HERE
    }
    
    @Override
        public boolean puedeReproducirse() {
        return estaVivo() && obtenerEnergia() > 60;
}

    @Override
public void reproducirse(Ecosistema eco) {
    if (!puedeReproducirse()) {
        return;
    }

    boolean hayOtroConejoVivo = false;

    for (Conejo otro : eco.obtenerConejos()) {
        if (otro != this && otro.estaVivo()) {
            hayOtroConejoVivo = true;
            break;
        }
    }

    if (hayOtroConejoVivo) {
        Conejo nuevoConejo = new Conejo(
                "Conejo-" + System.nanoTime(), 30, 5, 2.5
        );
        eco.obtenerConejos().add(nuevoConejo);
    }
}
    
    @Override
    protected void mostrarEstado(){
        
        System.out.println("Nombre: " + obtenerNombre());
        System.out.println("Energía: " + obtenerEnergia());
        
        if(obtenerEnergia() < 20){
            System.out.println("Animal en peligro!");
        }
    }
}
