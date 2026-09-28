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
        this.energia = 30.0 + (Math.random() * 70.0);
        this.edad = 0;
        this.viva = true;
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
        if (energia < 0){
            System.out.println("La energía no puedes ser menor a 0. Elige un valor válido");
        } else {
            this.energia = energia;
        }
    }
    
    protected void establecerEdad(int edad){
        this.edad = edad;
    }
    
    protected void establecerViva(boolean viva){
        this.viva = viva;
    }
    
    protected void envejecer(){
        this.edad = edad + 1;
        this.energia = energia - 5;
    }
}
