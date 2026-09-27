/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ie1.interfazgrafica;

import java.util.Scanner;

/**
 *
 * @author alqui
 */
public class IE1InterfazGrafica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int plantas;
        int conejos;
        int lobos;
        String climaInicial;
        int turnos;
        
        System.out.println("Bienvenido al juego!");
        System.out.println("Por favor ingresa la cantidad inicial de plantas (solo se permite un valor entre 5 y 30): ");
        plantas = sc.nextInt();
        
        
        System.out.println("Ahora ingresa la cantidad inicial de conejos (solo se permite un valor entre 2 y 15): ");
        conejos = sc.nextInt();
        
        System.out.println("A continuación ingresa la cantidad inicial de lobos (solo se permite un valor entre 1 y 5): ");
        lobos = sc.nextInt();
        
        System.out.println("Elige el clima inicial (soleado, lluvioso, sequia o invierno): ");
        climaInicial = sc.nextLine();
        
        System.out.println("Para comenzar a jugar, elige la cantidad de turnos de la simulación (entre 10 a 50): ");
        turnos = sc.nextInt();
        
        System.out.println("Estos son los valores ingresados:");
        System.out.println("Plantas: " + plantas);
        System.out.println("Conejos: " + conejos);
        System.out.println("Lobos: " + lobos);
        
        System.out.println("Ingresa 'OK' para iniciar. Ingresa 'Cambiar' para cambiar algún valor");
        
        sc.close();
    }
    
    /*void cambiarValorDeInicio(String entidad, int cantidad){
        switch (entidad) {
            case "Plantas":
                plantas = sc.nextInt();
                break;
            case "Conejos":
                conejos = sc.nextInt());
                break;
            case "Lobos":
                lobos = sc.nextInt();
                break;
            default:
                System.out.println("Opción no válida. Intenta de nuevo.");
        }
    }*/
}
