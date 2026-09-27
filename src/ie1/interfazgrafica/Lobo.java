/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

/**
 *
 * @author alqui
 */
public class Lobo extends Animal{
    int exitosCaza;
    
    public Lobo(String nombre, double energia, int velocidad, double peso, int cazas){
        super(nombre, energia, velocidad, peso);
        this.exitosCaza = cazas;
    }
    
    @Override
    protected void actuar(Ecosistema eco){
        ///CODE GOES HERE
    }
    
    @Override
    protected void comer(Ecosistema eco){
        ///CODE GOES HERE
    }
    
    @Override
    protected void mostrarEstado(){
        System.out.println("Nombre: " + obtenerNombre());
        System.out.println("Energía: " + obtenerEnergia());
        System.out.println("Cacerías exitosas: " + this.exitosCaza);
    }
}
