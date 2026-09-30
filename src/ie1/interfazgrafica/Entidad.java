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
    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;
    public Entidad(String nombre, double energia, int edad){
    establecerNombre(nombre);
    establecerEnergia(energia);
    establecerEdad(edad);
    this.viva = obtenerEnergia() > 0;
}
    
    abstract void actuar(Ecosistema eco);
    abstract void mostrarEstado();
    
    // ------------------------------------------------ GETTERS
    
    public String obtenerNombre() {
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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio.");
        }

        this.nombre = nombre.trim();
    }
    
   protected void establecerEnergia(double energia){
        if (!Double.isFinite(energia)) {
            throw new IllegalArgumentException(
                    "La energia debe ser un numero finito.");
        }

        this.energia = Math.max(0.0, energia);
    }
    
    protected void establecerEdad(int edad){
        this.edad = Math.max(0, edad);
    }
    
    protected void establecerViva(boolean viva){
        this.viva = viva;
    }
    
    protected void envejecer(){
        this.edad = this.edad + 1;
        establecerEnergia(this.energia - 5);
    }
}
