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
        Clima climaInicial;
        int turnos;
        
        Planta nuevo = new Planta("planta");
        System.out.println(nuevo.obtenerEnergia());
        System.out.println("Bienvenido al juego!");
        
        plantas = validarCantidadEntidades(
                sc,
                "Por favor ingresa la cantidad inicial de plantas (solo se permite un valor entre 5 y 30): ",
                5,
                30
            );
        
        conejos = validarCantidadEntidades(
                sc, 
                "Ahora ingresa la cantidad inicial de conejos (solo se permite un valor entre 2 y 15): ",
                2,
                15
            );  
        
        lobos = validarCantidadEntidades(
                sc,
                "A continuacion ingresa la cantidad inicial de lobos (solo se permite un valor entre 1 y 5): ",
                1,
                5
            );
        
        
        climaInicial = validarClima(sc);
        
        System.out.println();
        turnos = validarCantidadEntidades(
                sc,
                "Para comenzar a jugar, elige la cantidad de turnos de la simulacion (entre 10 a 50): ",
                10,
                50
            );
        
        System.out.println("Estos son los valores ingresados:");
        System.out.println("Plantas: " + plantas);
        System.out.println("Conejos: " + conejos);
        System.out.println("Lobos: " + lobos);
        System.out.println("Clima elegido: " + climaInicial);
        System.out.println("Turnos ingresados: " + turnos);
        
        validarConfirmacion(sc);
        
        sc.close();
    }
    
    /////////////////////////////////////////////////////////////////VALIDACIONES
    
    static int validarCantidadEntidades(
        Scanner sc, String mensaje, int min, int max) {

    System.out.println(mensaje);

    while (true) {
        String entrada = sc.nextLine().trim();

        try {
            int cantidad = Integer.parseInt(entrada);

            if (cantidad >= min && cantidad <= max) {
                return cantidad;
            }

            System.out.println(
                    "Ingrese un numero entre " + min + " y " + max);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero valido.");
        }
    }
}
    
    static Clima validarClima(Scanner sc){
        System.out.println("Elige el clima inicial (soleado, lluvioso, sequia o invierno): ");
        
        while (true) {
            String entrada = sc.nextLine().trim();

            for (Clima c : Clima.values()) {
                if (c.name().equalsIgnoreCase(entrada)) {
                    return c;
                }
            }
        System.out.println("Por favor, ingrese un clima valido (soleado, lluvioso, sequia o invierno)");
        }
    }
    
    static void validarConfirmacion(Scanner sc){
        System.out.println("Ingresa 'OK' para iniciar.");
       
        String ok = sc.nextLine();
        
        while (!ok.equalsIgnoreCase("OK") && !ok.equalsIgnoreCase("'OK'")) {
            System.out.println("Por favor ingrese 'OK'");
            ok = sc.nextLine().trim();
        }
    }
    
    static void crearEntidadesIniciales(int plantas, int conejos, int lobos, Ecosistema eco){
        for(int i = 1; i <= plantas; i++){
            Planta nuevaPlanta = new Planta("Planta-" + System.nanoTime());
            eco.obtenerPlantas().add(nuevaPlanta);
        }
        
        /*for(int i = 1; i > conejos; i++){
            Conejo nuevoConejo = new Conejo("Conejo-" + System.nanoTime());
            eco.plantas.add(nuevoConejo);
        }
        
        for(int i = 1; i > lobos; i++){
            Lobo nuevoLobo = new Lobo("Planta-" + System.nanoTime());
            eco.plantas.add(nievoLobo);
        }*/
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
