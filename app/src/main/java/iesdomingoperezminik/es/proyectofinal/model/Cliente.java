package iesdomingoperezminik.es.proyectofinal.model;

public class Cliente {

    private int id;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellidos;
    private boolean reservaActiva;

    public Cliente() {}

    public Cliente(String nombre, String apellidos, String email, String contraseña) {
        if(email == null || contraseña == null || nombre == null || apellidos == null)
            throw new NullPointerException("Algún parámetro es nulo en la creación del cliente.");

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contraseña = contraseña;
    }

    public Cliente(int id, String nombre, String apellidos, String email, String contraseña) {
        this(nombre, apellidos, email, contraseña);
        this.id = id;
    }

    public Cliente(int id, String nombre, String apellidos, String email, String contraseña, boolean reservaActiva) {
        this(id, nombre, apellidos, email, contraseña);
        this.reservaActiva = reservaActiva;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isReservaActiva() {
        return reservaActiva;
    }

    public void setReservaActiva(boolean reservaActiva) {
        this.reservaActiva = reservaActiva;
    }
}
