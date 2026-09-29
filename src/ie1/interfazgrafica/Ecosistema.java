/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ie1.interfazgrafica;

import java.util.ArrayList;

public class Ecosistema {

    private ArrayList<Planta> plantas = new ArrayList<>();
    private ArrayList<Conejo> conejos = new ArrayList<>();
    private ArrayList<Lobo> lobos = new ArrayList<>();
    private ArrayList<String> eventosTurno = new ArrayList<>();
    private ArrayList<Entidad> muertesRegistradas = new ArrayList<>();
    private ArrayList<Entidad> nacimientosRegistrados = new ArrayList<>();

    private Clima climaActual;
    private int turnoActual;
    private int siguienteId = 1;
    private int totalLobosCreados = 0;

    public void procesarTurno() {
        // Se implementará en la segunda parte.
    }

    public void agregarEntidad(String tipo) {
        double energiaInicial = 30.0 + Math.random() * 70.0;
        agregarEntidad(tipo, energiaInicial);
    }

    public void agregarEntidad(String tipo, double energiaInicial) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el tipo de entidad.");
        }

        if (!Double.isFinite(energiaInicial) || energiaInicial <= 0) {
            throw new IllegalArgumentException(
                    "La entidad debe ingresar con energia positiva y finita.");
        }

        tipo = tipo.trim();

        if (tipo.equalsIgnoreCase("planta")) {
            Planta planta = new Planta(
                    "Planta-" + siguienteId, energiaInicial);
            plantas.add(planta);

        } else if (tipo.equalsIgnoreCase("planta venenosa")) {
            Planta planta = new PlantaVenenosa(
                    "Planta-" + siguienteId, energiaInicial);
            plantas.add(planta);

        } else if (tipo.equalsIgnoreCase("conejo")) {
            Conejo conejo = new Conejo(
                    "Conejo-" + siguienteId,
                    energiaInicial,
                    5,
                    2.5);
            conejos.add(conejo);

        } else if (tipo.equalsIgnoreCase("lobo")) {
            if (totalLobosCreados >= 5) {
                throw new IllegalStateException(
                        "Ya se incorporaron los cinco lobos permitidos.");
            }

            Lobo lobo = new Lobo(
                    "Lobo-" + siguienteId,
                    energiaInicial,
                    8,
                    35.0,
                    0);
            lobos.add(lobo);
            totalLobosCreados++;

        } else {
            throw new IllegalArgumentException(
                    "Tipo de entidad desconocido: " + tipo);
        }

        siguienteId++;
    }

    public void cambiarClima(Clima nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException(
                    "El clima no puede ser nulo.");
        }

        this.climaActual = nuevo;
    }

    public void mostrarEstado() {
        int plantasVivas = 0;
        int conejosVivos = 0;
        int lobosVivos = 0;

        for (Planta planta : plantas) {
            if (planta.obtenerViva()) {
                plantasVivas++;
            }
        }

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo()) {
                conejosVivos++;
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                lobosVivos++;
            }
        }

        System.out.println();
        System.out.println("=== ESTADO DEL ECOSISTEMA ===");
        System.out.println("Turno: " + turnoActual);
        System.out.println("Clima: " + climaActual);
        System.out.println("Plantas vivas: " + plantasVivas);
        System.out.println("Conejos vivos: " + conejosVivos);
        System.out.println("Lobos vivos: " + lobosVivos);

        System.out.println("--- Eventos del turno ---");

        if (eventosTurno.isEmpty()) {
            System.out.println("Sin eventos.");
        } else {
            for (String evento : eventosTurno) {
                System.out.println("- " + evento);
            }
        }
    }

    public boolean ecosistemaColapsado() {
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

    public void generarReporteFinal() {
        // Se implementará en la segunda parte.
    }

    Clima obtenerClimaActual() {
        return climaActual;
    }

    public ArrayList<Planta> obtenerPlantas() {
        return new ArrayList<>(plantas);
    }

    public ArrayList<Conejo> obtenerConejos() {
        return new ArrayList<>(conejos);
    }

    public ArrayList<Lobo> obtenerLobos() {
        return new ArrayList<>(lobos);
    }

    public int obtenerTurnoActual() {
        return this.turnoActual;
    }

    public void establecerTurnoActual(int turnoActual) {
        if (turnoActual < 0) {
            throw new IllegalArgumentException(
                    "El turno no puede ser negativo.");
        }

        this.turnoActual = turnoActual;
    }

    public void registrarEvento(String evento) {
        if (evento == null || evento.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El evento no puede estar vacio.");
        }

        eventosTurno.add(evento.trim());
    }

    public ArrayList<String> obtenerEventosTurno() {
        return new ArrayList<>(eventosTurno);
    }

    public void limpiarEventosTurno() {
        eventosTurno.clear();
    }

    public void registrarMuerte(Entidad entidad, String causa) {
        if (entidad == null || entidad.obtenerViva()) {
            throw new IllegalArgumentException(
                    "Solo se puede registrar una entidad muerta.");
        }

        if (causa == null || causa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe indicar la causa de muerte.");
        }

        if (muertesRegistradas.contains(entidad)) {
            return;
        }

        muertesRegistradas.add(entidad);

        registrarEvento(
                entidad.obtenerNombre()
                + " murio: "
                + causa.trim()
                + ".");
    }

    public ArrayList<Entidad> obtenerMuertesRegistradas() {
        return new ArrayList<>(muertesRegistradas);
    }

    public void registrarNacimiento(Entidad nueva) {
        if (nueva == null
                || !nueva.obtenerViva()
                || nueva.obtenerEnergia() <= 0) {
            throw new IllegalArgumentException(
                    "La cria debe estar viva y tener energia.");
        }

        if (nacimientosRegistrados.contains(nueva)) {
            return;
        }

        if (plantas.contains(nueva)
                || conejos.contains(nueva)
                || lobos.contains(nueva)) {
            throw new IllegalArgumentException(
                    "La entidad ya pertenece al ecosistema.");
        }

        if (nueva instanceof Planta) {
            plantas.add((Planta) nueva);

        } else if (nueva instanceof Conejo) {
            conejos.add((Conejo) nueva);

        } else {
            throw new IllegalArgumentException(
                    "Solo pueden nacer plantas y conejos.");
        }

        nacimientosRegistrados.add(nueva);

        registrarEvento(
                "Nacio " + nueva.obtenerNombre() + ".");
    }

    public ArrayList<Entidad> obtenerNacimientosRegistrados() {
        return new ArrayList<>(nacimientosRegistrados);
    }
}