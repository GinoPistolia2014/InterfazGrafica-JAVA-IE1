/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public abstract class Entidad {
    protected String nombre;
    protected double energia;
    protected int edad;
    protected boolean viva;
    
    public Entidad(String nombre, double energia, int edad){
        this.nombre = nombre;
        this.energia = Math.max(0.0, energia);
        this.edad = Math.max(0, edad);
        this.viva = this.energia > 0;
    }
    
    abstract void actuar(Ecosistema eco);
    abstract void mostrarEstado();
    
    // ------------------------------------------------ GETTERS
    
    protected String obtenerNombre(){
        return this.nombre;
    }
    
    public double obtenerEnergia(){
        return this.energia;
    }
    
    protected int obtenerEdad(){
        return this.edad;
    }
    
    protected boolean obtenerViva(){
        return this.viva;
    }
    
    // ------------------------------------------------- SETTERS
    
    protected void establecerNombre(String nombre){
        this.nombre = nombre;
    }
    
   protected void establecerEnergia(double energia){
    this.energia = Math.max(0.0, energia);
}
    
    protected void establecerEdad(int edad){
        this.edad = edad;
    }
    
    protected void establecerViva(boolean viva){
        this.viva = viva;
    }
    
    protected void envejecer(){
    this.edad = this.edad + 1;
    establecerEnergia(this.energia - 5);
}
}
