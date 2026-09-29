/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 

package ie1.interfazgrafica;

public class Planta extends Entidad implements Reproducible, Mortal {

    private int tamanio;

    public Planta(String nombre) {
        this(nombre, 30.0 + Math.random() * 70.0);
    }

    public Planta(String nombre, double energia) {
        super(nombre, energia, 0);
        establecerTamanio((int) (Math.random() * 5) + 1);
    }

    @Override
    protected void actuar(Ecosistema eco) {
        intentarReproduccion(eco);
    }

    @Override
    public boolean puedeReproducirse() {
        return obtenerViva() && obtenerEnergia() > 30;
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        if (!puedeReproducirse()) {
            return;
        }

        double probabilidad =
                calcularProbabilidadReproduccion(
                        eco.obtenerClimaActual());

        if (Math.random() >= probabilidad) {
            return;
        }

        Planta nueva = crearDescendiente(
                "Planta-" + System.nanoTime());

        eco.registrarNacimiento(nueva);
        establecerEnergia(obtenerEnergia() - 20);
    }

    @Override
    protected void mostrarEstado() {
        System.out.println("Nombre: " + obtenerNombre());
        System.out.println("Energía: " + obtenerEnergia());
        System.out.println("Tamaño: " + this.tamanio);
    }

    public int serComida() {
        if (!obtenerViva()) {
            return 0;
        }

        int valorNutritivo = this.tamanio * 10;

        establecerEnergia(0);
        establecerViva(false);

        return valorNutritivo;
    }

    @Override
    public boolean estaVivo() {
        return obtenerViva();
    }

    @Override
    public void morir() {
        establecerEnergia(0);
        establecerViva(false);
    }

    public int obtenerTamanio() {
        return this.tamanio;
    }

    public void establecerTamanio(int tamanio) {
        if (tamanio < 1 || tamanio > 5) {
            throw new IllegalArgumentException(
                    "El tamanio debe estar entre 1 y 5.");
        }

        this.tamanio = tamanio;
    }

    public double calcularProbabilidadReproduccion(Clima clima) {
        if (clima == null) {
            throw new IllegalArgumentException(
                    "Debe configurar el clima antes de reproducir plantas.");
        }

        double probabilidadBase = 0.40;

        switch (clima) {
            case soleado:
                return probabilidadBase * 1.5;

            case lluvioso:
                return probabilidadBase * 2.0;

            case sequia:
                return probabilidadBase * 0.5;

            case invierno:
                return 0.0;

            default:
                throw new IllegalArgumentException(
                        "Clima desconocido.");
        }
    }

    protected Planta crearDescendiente(String nombre) {
        return new Planta(nombre, 40);
    }
}