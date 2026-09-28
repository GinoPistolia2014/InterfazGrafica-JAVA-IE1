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
    private ArrayList<Planta> plantas = new ArrayList<>();
    private ArrayList<Conejo> conejos = new ArrayList<>();
    private ArrayList<Lobo> lobos = new ArrayList<>();
    private Clima climaActual;
    private int turnoActual;
    
    void procesarTurno(){
        ///CODE GOES HERE
    };
    
    void agregarEntidad(String tipo){
        ///CODE GOES HERE
    };
    
    public void cambiarClima(Clima nuevo){
    if (nuevo == null) {
        throw new IllegalArgumentException(
                "El clima no puede ser nulo.");
    }

    this.climaActual = nuevo;
}
    
    public boolean ecosistemaColapsado(){
    boolean hayPlantas = false;
    boolean hayConejos = false;
    boolean hayLobos = false;

    for (Planta planta : plantas) {
        if (planta.obtenerViva()) {
            hayPlantas = true;
            break;
        }
    }

    for (Conejo conejo : conejos) {
        if (conejo.estaVivo()) {
            hayConejos = true;
            break;
        }
    }

    for (Lobo lobo : lobos) {
        if (lobo.estaVivo()) {
            hayLobos = true;
            break;
        }
    }

    return !hayPlantas || !hayConejos || !hayLobos;
}
    
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
    
    public int obtenerTurnoActual(){
    return this.turnoActual;
}

public void establecerTurnoActual(int turnoActual){
    if (turnoActual < 0) {
        throw new IllegalArgumentException(
                "El turno no puede ser negativo.");
    }

    this.turnoActual = turnoActual;
}
}
