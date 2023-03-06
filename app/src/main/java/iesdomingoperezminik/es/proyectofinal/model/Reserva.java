package iesdomingoperezminik.es.proyectofinal.model;

import java.time.LocalDate;

public class Reserva {

    private int id;
    private int id_cliente;
    private int numHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    private String nombreCliente;
    private String apellidosCliente;
    private String emailCliente;

    public Reserva() {}

    public Reserva(int id, int id_cliente, int numHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if(fechaEntrada == null || fechaSalida == null)
            throw new NullPointerException("Algún parámetro de creación de la reserva es nulo.");
        this.id = id;
        this.id_cliente = id_cliente;
        this.numHabitacion = numHabitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public Reserva(int id, int id_cliente, String nombreCliente, String apellidosCliente, String emailCliente, int numHabitacion, LocalDate enterDate, LocalDate leaveDate) {
        this(id, id_cliente, numHabitacion, enterDate, leaveDate);
        this.nombreCliente = nombreCliente;
        this.apellidosCliente = apellidosCliente;
        this.emailCliente = emailCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
