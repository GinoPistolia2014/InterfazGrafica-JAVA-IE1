/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
abstract class Animal extends Entidad implements Mortal{
    private int velocidad;
    private double peso;
    
    public Animal(String nombre, int velocidad, double peso){
        super(nombre);
        this.velocidad = velocidad;
        this.peso = peso;
    }
    
    abstract void comer(Ecosistema eco);
    
    protected void moverse(){
        ///CODE GOES HERE
        System.out.println("El animal se ha movido");
    };
    
    @Override
    public boolean estaVivo(){
        return obtenerViva();
    };
    
    @Override
    public void morir(){
        establecerEnergia(0);
    };
}
