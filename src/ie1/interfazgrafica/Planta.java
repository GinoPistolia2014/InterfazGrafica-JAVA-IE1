/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public class Planta extends Entidad implements Reproducible{
    private int tamanio;
    
    public Planta(String nombre){
        super(nombre);
        this.tamanio = (int)(Math.random() * 5) + 1;
    }
    
    @Override
    protected void actuar(Ecosistema eco){
        if(obtenerEnergia() > 5 && eco.obtenerClimaActual() == Clima.invierno){
            intentarReproduccion(eco);
        }
    }
    
    @Override
    public boolean puedeReproducirse() {
        return obtenerEnergia() > 30;
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        Planta nueva = new Planta("Planta-" + System.nanoTime());
        eco.obtenerPlantas().add(nueva);
    }
    
    @Override
    protected void mostrarEstado(){
        
        System.out.println("Nombre: " + obtenerNombre());
        System.out.println("Energía: " + obtenerEnergia());
        System.out.println("Tamaño: " + this.tamanio);
    }
    
    private int serComida(){
        establecerEnergia(3.0);
        this.tamanio = tamanio * 10;
        return this.tamanio;
    }
}
