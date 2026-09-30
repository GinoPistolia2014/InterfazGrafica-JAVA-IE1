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

    // Historial turno a turno (la posicion 0 es el estado inicial).
    private ArrayList<Integer> historialPlantas = new ArrayList<>();
    private ArrayList<Integer> historialConejos = new ArrayList<>();
    private ArrayList<Integer> historialLobos = new ArrayList<>();

    // Eventos de cada turno (la posicion 0 corresponde al turno 1).
    private ArrayList<ArrayList<String>> historialEventos = new ArrayList<>();

    private Clima climaActual;
    private int turnoActual;
    private int siguienteId = 1;
    private int totalLobosCreados = 0;

    // ------------------------------------------------ TURNO

    public void procesarTurno() {
        if (climaActual == null) {
            throw new IllegalStateException(
                    "Debe configurar el clima antes de iniciar la simulacion.");
        }

        // Antes del primer turno se guarda el estado inicial (turno 0).
        if (historialPlantas.isEmpty()) {
            guardarPoblaciones();
        }

        turnoActual++;

        System.out.println();
        System.out.println(
                "=== TURNO " + turnoActual + " | Clima: " + climaActual + " ===");
        System.out.println(
                "Plantas: " + contarPlantasVivas()
                + "  Conejos: " + contarConejosVivos()
                + "  Lobos: " + contarLobosVivos());

        // 1 y 2. Las plantas se reproducen; los conejos comen y se reproducen.
        // Se usa un solo ArrayList<Reproducible> con las plantas primero y
        // los conejos despues, asi cada uno intenta reproducirse una sola vez.
        ArrayList<Reproducible> reproducibles = new ArrayList<>();

        for (Planta planta : plantas) {
            if (planta.estaVivo()) {
                reproducibles.add(planta);
            }
        }

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo()) {
                reproducibles.add(conejo);
            }
        }

        for (Reproducible reproducible : reproducibles) {
            Mortal ser = (Mortal) reproducible;

            // Pudo morir antes en este mismo turno (por ejemplo, una planta comida).
            if (!ser.estaVivo()) {
                continue;
            }

            // Los conejos comen antes de intentar reproducirse.
            if (reproducible instanceof Conejo) {
                ((Conejo) reproducible).comer(this);
            }

            reproducible.intentarReproduccion(this);
        }

        // 3. Los lobos intentan cazar.
        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                lobo.actuar(this);
            }
        }

        // 4. Todas las entidades envejecen y gastan energia base.
        for (Entidad entidad : obtenerEntidadesVivas()) {
            entidad.envejecer();
        }

        // 5. Efectos del clima sobre la energia de los animales.
        aplicarEfectosClima();

        // 6. Las entidades sin energia mueren.
        verificarMuertes();

        // 7. Se muestra el estado y se guardan los eventos antes de limpiarlos.
        mostrarEstado();
        guardarPoblaciones();
        historialEventos.add(new ArrayList<>(eventosTurno));
        limpiarEventosTurno();
    }

    private void aplicarEfectosClima() {
        int efectoConejos = 0;
        int efectoLobos = 0;

        switch (climaActual) {
            case soleado:
                efectoConejos = 5;
                break;

            case lluvioso:
                efectoConejos = 3;
                efectoLobos = -5;
                break;

            case sequia:
                efectoConejos = -5;
                break;

            case invierno:
                // La bonificacion de caza (+20%) esta en Lobo.calcularProbabilidadCaza().
                efectoConejos = -8;
                break;
        }

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo()) {
                conejo.establecerEnergia(conejo.obtenerEnergia() + efectoConejos);
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                lobo.establecerEnergia(lobo.obtenerEnergia() + efectoLobos);
            }
        }

        String efecto = "Efecto del clima " + climaActual
                + ": conejos " + String.format("%+d", efectoConejos) + " de energia";

        if (efectoLobos != 0) {
            efecto += ", lobos " + String.format("%+d", efectoLobos) + " de energia";
        }

        registrarEvento(efecto + ".");
    }

    private void verificarMuertes() {
        // verificarMuerte() es el metodo default de la interfaz Mortal.
        for (Planta planta : plantas) {
            if (planta.verificarMuerte()) {
                registrarMuerte(planta, "se seco por falta de energia");
            }
        }

        for (Conejo conejo : conejos) {
            if (conejo.verificarMuerte()) {
                registrarMuerte(conejo, "de inanicion (sin energia)");
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.verificarMuerte()) {
                registrarMuerte(lobo, "de inanicion (sin energia)");
            }
        }
    }

    private ArrayList<Entidad> obtenerEntidadesVivas() {
        ArrayList<Entidad> vivas = new ArrayList<>();

        for (Planta planta : plantas) {
            if (planta.estaVivo()) {
                vivas.add(planta);
            }
        }

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo()) {
                vivas.add(conejo);
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                vivas.add(lobo);
            }
        }

        return vivas;
    }

    private void guardarPoblaciones() {
        historialPlantas.add(contarPlantasVivas());
        historialConejos.add(contarConejosVivos());
        historialLobos.add(contarLobosVivos());
    }

    // ------------------------------------------------ ENTIDADES

    public Entidad agregarEntidad(String tipo) {
        double energiaInicial = 30.0 + Math.random() * 70.0;
        return agregarEntidad(tipo, energiaInicial);
    }

    public Entidad agregarEntidad(String tipo, double energiaInicial) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el tipo de entidad.");
        }

        if (!Double.isFinite(energiaInicial) || energiaInicial <= 0) {
            throw new IllegalArgumentException(
                    "La entidad debe ingresar con energia positiva y finita.");
        }

        tipo = tipo.trim();

        Entidad nueva;

        if (tipo.equalsIgnoreCase("planta")) {
            Planta planta = new Planta(generarNombre("Planta"), energiaInicial);
            plantas.add(planta);
            nueva = planta;

        } else if (tipo.equalsIgnoreCase("planta venenosa")) {
            // Se llama "Planta-N" igual que las demas para que no se distinga.
            Planta planta = new PlantaVenenosa(generarNombre("Planta"), energiaInicial);
            plantas.add(planta);
            nueva = planta;

        } else if (tipo.equalsIgnoreCase("conejo")) {
            Conejo conejo = new Conejo(
                    generarNombre("Conejo"),
                    energiaInicial,
                    5,
                    2.5);
            conejos.add(conejo);
            nueva = conejo;

        } else if (tipo.equalsIgnoreCase("lobo")) {
            if (totalLobosCreados >= 5) {
                throw new IllegalStateException(
                        "Ya se incorporaron los cinco lobos permitidos.");
            }

            Lobo lobo = new Lobo(
                    generarNombre("Lobo"),
                    energiaInicial,
                    8,
                    35.0,
                    0);
            lobos.add(lobo);
            totalLobosCreados++;
            nueva = lobo;

        } else {
            throw new IllegalArgumentException(
                    "Tipo de entidad desconocido: " + tipo);
        }

        return nueva;
    }

    public String generarNombre(String prefijo) {
        String nombre = prefijo + "-" + siguienteId;
        siguienteId++;
        return nombre;
    }

    public int obtenerTotalLobosCreados() {
        return this.totalLobosCreados;
    }

    public void cambiarClima(Clima nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException(
                    "El clima no puede ser nulo.");
        }

        this.climaActual = nuevo;
    }

    // ------------------------------------------------ ESTADO

    public void mostrarEstado() {
        System.out.println("-- Eventos --");

        if (eventosTurno.isEmpty()) {
            System.out.println("  Sin eventos.");
        } else {
            for (String evento : eventosTurno) {
                System.out.println("  " + evento);
            }
        }

        System.out.println(
                "Estado: Plantas: " + contarPlantasVivas()
                + "  Conejos: " + contarConejosVivos()
                + "  Lobos: " + contarLobosVivos()
                + "  | Clima: " + climaActual);

        String enPeligro = "";

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo() && conejo.obtenerEnergia() < 20) {
                enPeligro += " " + conejo.obtenerNombre()
                        + " (energia " + (int) conejo.obtenerEnergia() + ")";
            }
        }

        if (!enPeligro.isEmpty()) {
            System.out.println("[PELIGRO] Conejos con poca energia:" + enPeligro);
        }
    }

    public int contarPlantasVivas() {
        int vivas = 0;

        for (Planta planta : plantas) {
            if (planta.estaVivo()) {
                vivas++;
            }
        }

        return vivas;
    }

    public int contarConejosVivos() {
        int vivos = 0;

        for (Conejo conejo : conejos) {
            if (conejo.estaVivo()) {
                vivos++;
            }
        }

        return vivos;
    }

    public int contarLobosVivos() {
        int vivos = 0;

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                vivos++;
            }
        }

        return vivos;
    }

    public boolean ecosistemaColapsado() {
        return contarPlantasVivas() == 0
                || contarConejosVivos() == 0
                || contarLobosVivos() == 0;
    }

        // ------------------------------------------------ REPORTE FINAL

    public void generarReporteFinal() {
        System.out.println();
        System.out.println("============== REPORTE FINAL ==============");
        System.out.println("Turnos jugados: " + turnoActual);

        // 1. Causa de fin.
        if (ecosistemaColapsado()) {
            String extintas = "";

            if (contarPlantasVivas() == 0) {
                extintas += " plantas";
            }

            if (contarConejosVivos() == 0) {
                extintas += " conejos";
            }

            if (contarLobosVivos() == 0) {
                extintas += " lobos";
            }

            System.out.println("Causa de fin: colapso del ecosistema.");
            System.out.println("Poblacion extinguida:" + extintas);
        } else {
            System.out.println(
                    "Causa de fin: se completaron los " + turnoActual + " turnos configurados.");
        }

        System.out.println(
                "Poblacion final: Plantas: " + contarPlantasVivas()
                + "  Conejos: " + contarConejosVivos()
                + "  Lobos: " + contarLobosVivos());

        // 2. Turno de mayor actividad.
        int turnoMasActivo = 0;
        int maxEventos = -1;

        for (int i = 0; i < historialEventos.size(); i++) {
            int cantidad = historialEventos.get(i).size();

            if (cantidad > maxEventos) {
                maxEventos = cantidad;
                turnoMasActivo = i + 1;
            }
        }

        System.out.println();
        if (turnoMasActivo > 0) {
            System.out.println(
                    "Turno de mayor actividad: turno " + turnoMasActivo
                    + " (" + maxEventos + " eventos).");
        }

        // 3. Entidad mas longeva de cada tipo.
        System.out.println();
        System.out.println("Entidad mas longeva de cada tipo:");
        mostrarMasLongeva("Planta", new ArrayList<Entidad>(plantas));
        mostrarMasLongeva("Conejo", new ArrayList<Entidad>(conejos));
        mostrarMasLongeva("Lobo", new ArrayList<Entidad>(lobos));

        // 4. Lobo con mas cacerias exitosas.
        Lobo mejorCazador = null;

        for (Lobo lobo : lobos) {
            if (mejorCazador == null
                    || lobo.obtenerExitosCaza() > mejorCazador.obtenerExitosCaza()) {
                mejorCazador = lobo;
            }
        }

        System.out.println();
        if (mejorCazador != null) {
            System.out.println(
                    "Lobo con mas cacerias exitosas: " + mejorCazador.obtenerNombre()
                    + " (" + mejorCazador.obtenerExitosCaza() + " cacerias).");
        }

        // 5. Nacimientos y muertes por tipo.
        int[] nacimientos = contarPorTipo(nacimientosRegistrados);
        int[] muertes = contarPorTipo(muertesRegistradas);

        System.out.println();
        System.out.println("Nacimientos y muertes por tipo:");
        System.out.println(
                "  Plantas: " + nacimientos[0] + " nacimientos, " + muertes[0] + " muertes");
        System.out.println(
                "  Conejos: " + nacimientos[1] + " nacimientos, " + muertes[1] + " muertes");
        System.out.println(
                "  Lobos:   " + nacimientos[2] + " nacimientos, " + muertes[2] + " muertes");

        // ---------------------------------- EXTRAS

        // Historial de poblaciones turno a turno.
        System.out.println();
        System.out.println("Historial de poblaciones:");
        System.out.println("Turno | Plantas | Conejos | Lobos");

        for (int turno = 0; turno < historialPlantas.size(); turno++) {
            System.out.printf(
                    "%5d | %7d | %7d | %5d%n",
                    turno,
                    historialPlantas.get(turno),
                    historialConejos.get(turno),
                    historialLobos.get(turno));
        }

        // Maximos y minimos de cada poblacion.
        System.out.println();
        System.out.println("Maximos y minimos (turno 0 = estado inicial):");
        mostrarMaximoYMinimo("Plantas", historialPlantas);
        mostrarMaximoYMinimo("Conejos", historialConejos);
        mostrarMaximoYMinimo("Lobos", historialLobos);

        // Entidades peligrosas vivas, ordenadas por nivel (de mayor a menor).
        ArrayList<Peligroso> peligrosos = new ArrayList<>();

        for (Planta planta : plantas) {
            if (planta.estaVivo() && planta instanceof Peligroso) {
                peligrosos.add((Peligroso) planta);
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                peligrosos.add(lobo);
            }
        }

        peligrosos.sort((a, b) -> b.getNivelPeligro() - a.getNivelPeligro());

        System.out.println();
        System.out.println("Entidades peligrosas (ordenadas por nivel de peligro):");

        if (peligrosos.isEmpty()) {
            System.out.println("  No quedan entidades peligrosas con vida.");
        } else {
            for (Peligroso peligroso : peligrosos) {
                System.out.println(
                        "  " + peligroso.obtenerNombre()
                        + " - nivel " + peligroso.getNivelPeligro());
            }
        }

        System.out.println("===========================================");
    }

    private void mostrarMasLongeva(String tipo, ArrayList<Entidad> lista) {
        Entidad masLongeva = null;

        for (Entidad entidad : lista) {
            if (masLongeva == null || entidad.obtenerEdad() > masLongeva.obtenerEdad()) {
                masLongeva = entidad;
            }
        }

        if (masLongeva == null) {
            System.out.println("  " + tipo + ": no hubo.");
            return;
        }

        String estado = masLongeva.obtenerViva() ? "sigue con vida" : "ya murio";

        System.out.println(
                "  " + tipo + ": " + masLongeva.obtenerNombre()
                + " (edad " + masLongeva.obtenerEdad() + ", " + estado + ")");
    }

    // Devuelve {plantas, conejos, lobos}.
    private int[] contarPorTipo(ArrayList<Entidad> lista) {
        int[] cantidades = new int[3];

        for (Entidad entidad : lista) {
            if (entidad instanceof Planta) {
                cantidades[0]++;
            } else if (entidad instanceof Conejo) {
                cantidades[1]++;
            } else if (entidad instanceof Lobo) {
                cantidades[2]++;
            }
        }

        return cantidades;
    }

    private void mostrarMaximoYMinimo(String nombre, ArrayList<Integer> historial) {
        if (historial.isEmpty()) {
            return;
        }

        int turnoMaximo = 0;
        int turnoMinimo = 0;

        for (int turno = 1; turno < historial.size(); turno++) {
            if (historial.get(turno) > historial.get(turnoMaximo)) {
                turnoMaximo = turno;
            }

            if (historial.get(turno) < historial.get(turnoMinimo)) {
                turnoMinimo = turno;
            }
        }

        System.out.println(
                "  " + nombre
                + ": maximo " + historial.get(turnoMaximo) + " (turno " + turnoMaximo + ")"
                + ", minimo " + historial.get(turnoMinimo) + " (turno " + turnoMinimo + ")");
    }

    // ------------------------------------------------ GETTERS

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

    // ------------------------------------------------ EVENTOS

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