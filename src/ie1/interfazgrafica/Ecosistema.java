/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie1.interfazgrafica;

import java.util.ArrayList;

/**
 *
 * @author alqui
 */
public class Ecosistema {
    ArrayList<Planta> plantas = new ArrayList<>();
    ArrayList<Conejo> conejos = new ArrayList<>();
    ArrayList<Lobo> lobos = new ArrayList<>();
    Clima climaActual;
    int turnoActual;
    
    void procesarTurno(){
        ///CODE GOES HERE
    };
    
    void agregarEntidad(String tipo){
        ///CODE GOES HERE
    };
    
    void cambiarClima(Clima nuevo){
        ///CODE GOES HERE
    };
    
    void ecosistemaColapsado(){
        ///CODE GOES HERE
    };
    
    void generarReporteFinal(){
        ///CODE GOES HERE
    };
    
    ///////////////////////////////////////// GETTERS
    Clima obtenerClimaActual(){
        return climaActual;
    }
    
    ArrayList<Planta> obtenerPlantas(){
        return plantas;
    }
    
    ArrayList<Lobo> obtenerLobos(){
        return lobos;
    }
    
    ArrayList<Conejo> obtenerConejos(){
        return conejos;
    }
}
