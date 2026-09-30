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

        Ecosistema eco = new Ecosistema();
        eco.cambiarClima(climaInicial);

        crearEntidadesIniciales(plantas, conejos, lobos, eco);

        System.out.println("Ecosistema inicial creado:");
        System.out.println("Plantas: " + eco.contarPlantasVivas());
        System.out.println("Conejos: " + eco.contarConejosVivos());
        System.out.println("Lobos: " + eco.contarLobosVivos());
        System.out.println("Clima: " + eco.obtenerClimaActual());

        // ------------------------------------------ LOOP PRINCIPAL
        // Avanza turno a turno hasta completar los turnos o hasta que colapse.
        while (eco.obtenerTurnoActual() < turnos && !eco.ecosistemaColapsado()) {
            System.out.println();
            System.out.println(
                    ">>> Presione Enter para jugar el turno "
                    + (eco.obtenerTurnoActual() + 1) + " de " + turnos + "...");
            sc.nextLine();

            eco.procesarTurno();

            boolean tocaIntervenir = eco.obtenerTurnoActual() % 3 == 0;
            boolean quedanTurnos = eco.obtenerTurnoActual() < turnos;

            if (tocaIntervenir && quedanTurnos && !eco.ecosistemaColapsado()) {
                menuIntervencion(sc, eco);
            }
        }

        if (eco.ecosistemaColapsado()) {
            System.out.println();
            System.out.println(
                    "El ecosistema colapso en el turno " + eco.obtenerTurnoActual() + ".");
        }

        eco.generarReporteFinal();

        // El Scanner se cierra recien cuando termina toda la simulacion.
        sc.close();
    }

    /////////////////////////////////////////////////////////////////INTERVENCION

    static void menuIntervencion(Scanner sc, Ecosistema eco) {
        System.out.println();
        System.out.println("=== INTERVENCION (cada 3 turnos) ===");
        System.out.println("1. Cambiar clima (actual: " + eco.obtenerClimaActual() + ")");
        System.out.println("2. Agregar entidad");
        System.out.println("3. Solo avanzar");

        int opcion = validarCantidadEntidades(sc, "Opcion: ", 1, 3);

        switch (opcion) {
            case 1:
                Clima nuevo = validarClima(
                        sc,
                        "Elige el nuevo clima (soleado, lluvioso, sequia o invierno): ");

                if (confirmar(sc, "Cambiar el clima de "
                        + eco.obtenerClimaActual() + " a " + nuevo + "?")) {
                    eco.cambiarClima(nuevo);
                    eco.registrarEvento("Intervencion: el clima cambio a " + nuevo + ".");
                    System.out.println("Clima cambiado a " + nuevo + ".");
                } else {
                    System.out.println("Accion cancelada. Se avanza sin intervenir.");
                }
                break;

            case 2:
                String tipo = validarTipoEntidad(sc, eco);

                if (tipo.equals("lobo") && eco.obtenerTotalLobosCreados() >= 5) {
                    System.out.println(
                            "No se puede agregar: ya se incorporaron los 5 lobos permitidos.");
                    break;
                }

                if (confirmar(sc, "Agregar " + tipo + " al ecosistema?")) {
                    try {
                        Entidad nueva = eco.agregarEntidad(tipo);
                        eco.registrarEvento(
                                "Intervencion: se agrego " + nueva.obtenerNombre() + ".");
                        System.out.println(
                                "Se agrego '" + nueva.obtenerNombre() + "' al ecosistema.");
                    } catch (IllegalStateException e) {
                        System.out.println("No se pudo agregar: " + e.getMessage());
                    }
                } else {
                    System.out.println("Accion cancelada. Se avanza sin intervenir.");
                }
                break;

            default:
                System.out.println("Se avanza sin intervenir.");
        }
    }

    static String validarTipoEntidad(Scanner sc, Ecosistema eco) {
        System.out.println(
                "Que entidad agregar? (planta / planta venenosa / conejo / lobo)"
                + " [lobos incorporados: " + eco.obtenerTotalLobosCreados() + "/5]: ");

        while (true) {
            String entrada = sc.nextLine().trim().toLowerCase();

            if (entrada.equals("planta")
                    || entrada.equals("planta venenosa")
                    || entrada.equals("conejo")
                    || entrada.equals("lobo")) {
                return entrada;
            }

            System.out.println(
                    "Ingrese una opcion valida: planta, planta venenosa, conejo o lobo.");
        }
    }

    static boolean confirmar(Scanner sc, String pregunta) {
        System.out.println(pregunta + " (s/n): ");

        while (true) {
            String respuesta = sc.nextLine().trim().toLowerCase();

            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                return true;
            }

            if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            }

            System.out.println("Responda 's' para confirmar o 'n' para cancelar.");
        }
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

    static Clima validarClima(Scanner sc) {
        return validarClima(
                sc,
                "Elige el clima inicial (soleado, lluvioso, sequia o invierno): ");
    }

    static Clima validarClima(Scanner sc, String mensaje) {
        System.out.println(mensaje);

        while (true) {
            // Se acepta "sequía" con tilde: se reemplaza la í por i.
            String entrada = sc.nextLine().trim().toLowerCase().replace("í", "i");

            for (Clima c : Clima.values()) {
                if (c.name().equalsIgnoreCase(entrada)) {
                    return c;
                }
            }

            System.out.println(
                    "Por favor, ingrese un clima valido (soleado, lluvioso, sequia o invierno)");
        }
    }

    static void validarConfirmacion(Scanner sc) {
        System.out.println("Ingresa 'OK' para iniciar.");

        String ok = sc.nextLine().trim();

        while (!ok.equalsIgnoreCase("OK") && !ok.equalsIgnoreCase("'OK'")) {
            System.out.println("Por favor ingrese 'OK'");
            ok = sc.nextLine().trim();
        }
    }

    static void crearEntidadesIniciales(
            int plantas, int conejos, int lobos, Ecosistema eco) {

        // Una de cada cinco plantas es venenosa. Se guarda en la misma lista
        // de plantas y el conejo no puede distinguirla antes de comerla.
        for (int i = 1; i <= plantas; i++) {
            if (i % 5 == 0) {
                eco.agregarEntidad("planta venenosa");
            } else {
                eco.agregarEntidad("planta");
            }
        }

        for (int i = 1; i <= conejos; i++) {
            eco.agregarEntidad("conejo");
        }

        for (int i = 1; i <= lobos; i++) {
            eco.agregarEntidad("lobo");
        }
    }
}