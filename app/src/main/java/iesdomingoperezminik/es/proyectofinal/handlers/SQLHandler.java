package iesdomingoperezminik.es.proyectofinal.handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.se.omapi.Session;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import iesdomingoperezminik.es.proyectofinal.model.Cliente;
import iesdomingoperezminik.es.proyectofinal.model.Reserva;

public class SQLHandler extends SQLiteOpenHelper {

    private static final String BD_NOMBRE = "clientes.db";
    private static final int BD_VERSION = 1;
    private SQLiteDatabase db = getWritableDatabase();

    public SQLHandler(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Clientes (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellidos TEXT, email TEXT, contraseña TEXT)");
        db.execSQL("CREATE TABLE Reservas (id INTEGER PRIMARY KEY AUTOINCREMENT, id_cliente INTEGER NOT NULL, numHabitacion INTEGER, fecha_entrada DATETIME, fecha_salida DATETIME, FOREIGN KEY(id_cliente) REFERENCES Clientes(id))");
        db.execSQL("CREATE TABLE Admin (id INTEGER PRIMARY KEY AUTOINCREMENT, admin_email TEXT)");
        db.execSQL(String.format("INSERT INTO Admin (admin_email) VALUES ('%s')", "donovancf12380@gmail.com"));
        db.execSQL(String.format("INSERT INTO Admin (admin_email) VALUES ('%s')", "test@test.com"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @SuppressLint("Range")
    public int getId(String email) {
        if(email == null)
            throw new NullPointerException("El email del que se quiere conocer la ID es nulo.");
        int res = -1;
        Cursor c = db.rawQuery("SELECT id FROM Clientes WHERE email = ?", new String[]{email}, null);
        if(c.moveToFirst())
            res = c.getInt(c.getColumnIndex("id"));
        return res;
    }

    public void insertarCliente(Cliente c) {
        if(c == null)
            throw new NullPointerException("El cliente a insertar es nulo.");
        db.execSQL("INSERT INTO Clientes (nombre, apellidos, email, contraseña) VALUES ('" + c.getNombre() + "', '" + c.getApellidos() + "', '" + c.getEmail() + "', '" + c.getContraseña() + "')");
    }

    public void updateCliente(Cliente old, Cliente nuevo) {
        if(old == null || nuevo == null)
            throw new NullPointerException("Algún parámetro para la actualización de los clientes es nulo.");

        db.execSQL(String.format("UPDATE Clientes SET nombre = '%s', apellidos = '%s', email = '%s', contraseña = '%s' WHERE id = '%d'", nuevo.getNombre(), nuevo.getApellidos(), nuevo.getEmail(), nuevo.getContraseña(), old.getId()));
    }

    public void deleteCliente(Cliente c) {
        if(c == null)
            throw new NullPointerException("El cliente a eliminar es nulo.");
        db.execSQL(String.format("DELETE FROM Clientes WHERE id = '%d'", c.getId()));
    }

    public void insertarReserva(Reserva r) {
        if(r == null)
            throw new NullPointerException("La reserva a insertar es nula.");
        db.execSQL(String.format("INSERT INTO Reservas (id_cliente, numHabitacion, fecha_entrada, fecha_salida) VALUES ('%d', '%d', '%s', '%s')", r.getId_cliente(),
                r.getNumHabitacion(), r.getFechaEntrada(), r.getFechaSalida()));
    }

    @SuppressLint("Range")
    public String getNombreApellidosCliente(int id) {
        String res = null;
        Cursor c = db.rawQuery("SELECT * FROM Clientes WHERE id = ?", new String[] {String.valueOf(id)});
        if(c.moveToFirst())
            res = c.getString(c.getColumnIndex("nombre")) + c.getString(c.getColumnIndex("apellidos"));
        return res;
    }

    @SuppressLint("Range")
    public String getEmail(int id) {
        String res = null;
        Cursor c = db.rawQuery("SELECT * FROM Clientes WHERE id = ?", new String[] {String.valueOf(id)});
        if(c.moveToFirst())
            res = c.getString(c.getColumnIndex("email"));
        return res;
    }

    @SuppressLint("Range")
    public Cliente getCliente(int id) {
        Cliente res = null;
        Cursor c = db.rawQuery("SELECT * FROM Clientes WHERE id = ?", new String[] { String.valueOf(id) });
        if(c.moveToFirst())
            res = new Cliente(c.getString(c.getColumnIndex("nombre")), c.getString(c.getColumnIndex("apellidos")),
                    c.getString(c.getColumnIndex("email")), c.getString(c.getColumnIndex("contraseña")));
        return res;
    }

    @SuppressLint("Range")
    public ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM Clientes", null)) {
            Cliente c;
            while (cursor.moveToNext())
                clientes.add((c = new Cliente(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")), cursor.getString(cursor.getColumnIndex("apellidos")),
                        cursor.getString(cursor.getColumnIndex("email")), cursor.getString(cursor.getColumnIndex("contraseña")))));
        }

        return clientes;
    }

    @SuppressLint({"Range", "NewApi"})
    public ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM Reservas", null)) {
            Reserva r;
            while (cursor.moveToNext())
                reservas.add((r = new Reserva(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getInt(cursor.getColumnIndex("id_cliente")), cursor.getInt(cursor.getColumnIndex("numHabitacion")),
                        LocalDate.parse(cursor.getString(cursor.getColumnIndex("fecha_entrada")), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(cursor.getString(cursor.getColumnIndex("fecha_salida")), DateTimeFormatter.ofPattern("yyyy-MM-dd")))));
        }
        return reservas;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAdmins() {
        ArrayList<String> admins = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM Admin", null)) {
            while (cursor.moveToNext())
                admins.add(cursor.getString(cursor.getColumnIndex("admin_email")));
        }
        return admins;
    }

    public void deleteReserva(Reserva r) {
        if(r == null)
            throw new NullPointerException("La reserva a eliminar es nula");
        db.execSQL("DELETE FROM Reservas WHERE id = ?", new String[] { String.valueOf(r.getId()) });
    }

    @SuppressLint("Recycle")
    public boolean esClienteDuplicado(String email) {
        return db.rawQuery("SELECT * FROM Clientes WHERE Email = ?", new String[] { email }).getCount() > 0;
    }

    @SuppressLint("Recycle")
    public boolean existeCliente(String email) {
        return db.rawQuery("SELECT * FROM Clientes WHERE Email = ?", new String[] { email }).getCount() == 1;
    }

    @SuppressLint("Recycle")
    public boolean existeAdmin(String email) {
        return db.rawQuery("SELECT * FROM Admin WHERE admin_email = ?", new String[] { email }).getCount() == 1;
    }

    @SuppressLint("Range")
    public String getNombreYApellidos(String email) {
        String res = null;
        Cursor c = db.rawQuery("SELECT nombre, apellidos FROM Clientes WHERE Email = ?", new String[] { email });
        if(c.moveToFirst())
            res = String.format("%s %s", c.getString(c.getColumnIndex("nombre")), c.getString(c.getColumnIndex("apellidos")));
        c.close();
        return res;
    }

    @SuppressLint("Range")
    public String getPassword(String email) {
        String res = null;
        Cursor c = db.rawQuery("SELECT contraseña FROM Clientes WHERE Email = ?", new String[] { email });
        if(c.moveToFirst())
            res = c.getString(c.getColumnIndex("contraseña"));
        c.close();
        return res;
    }

    public boolean tieneReservaActiva(int id) {
        return db.rawQuery("SELECT * FROM Reservas WHERE id_cliente = ?", new String[] { String.valueOf(id) }).getCount() != 0;
    }

    @SuppressLint("DefaultLocale")
    public void updateReservas(Reserva old, Reserva newReserva) {
        db.execSQL(String.format("UPDATE Reservas SET id_cliente = '%s', numHabitacion = '%s', fecha_entrada = '%s', fecha_salida = '%s' WHERE id = '%d'", newReserva.getId_cliente(), newReserva.getNumHabitacion(),
                newReserva.getFechaEntrada(), newReserva.getFechaSalida(), old.getId()));
    }

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Reserva getReserva(int id_cliente) {
        Reserva r = new Reserva();
        Cursor c = db.rawQuery("SELECT * FROM Reservas WHERE id_cliente = ?", new String[] { String.valueOf(id_cliente) });
        if(c.moveToFirst()) {
            r.setFechaEntrada(LocalDate.parse(c.getString(c.getColumnIndex("fecha_entrada"))));
            r.setFechaSalida(LocalDate.parse(c.getString(c.getColumnIndex("fecha_salida"))));
            r.setNumHabitacion(c.getInt(c.getColumnIndex("numHabitacion")));
        }
        return r;
    }

}
