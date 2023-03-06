package iesdomingoperezminik.es.proyectofinal.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import iesdomingoperezminik.es.proyectofinal.ClientesActivity;
import iesdomingoperezminik.es.proyectofinal.R;
import iesdomingoperezminik.es.proyectofinal.adapters.ReservasAdapter;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;
import iesdomingoperezminik.es.proyectofinal.model.Reserva;

public class EditReservaDialog extends Dialog {

    private SQLHandler handler;
    private Button buttonModificarFechaEntrada;
    private Button buttonModificarFechaSalida;
    private Button buttonAceptar;
    private Reserva old;
    private ArrayList<Reserva> reservas;
    private ReservasAdapter adapter;

    private Reserva newReserva;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public EditReservaDialog(@NonNull Context context, Reserva reserva, ArrayList<Reserva> reservas, ReservasAdapter adapter) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_reservas_form_dialog);
        this.old = reserva;
        this.reservas = reservas;
        this.adapter = adapter;

        handler = new SQLHandler(context);

        buttonModificarFechaEntrada = (Button) findViewById(R.id.button_aceptar_modificar_fecha_entrada_reserva);
        buttonModificarFechaEntrada.setOnClickListener(this::onAceptarModificarFechaEntrada);
        buttonModificarFechaEntrada = (Button) findViewById(R.id.button_aceptar_modificar_fecha_salida_reserva);
        buttonModificarFechaEntrada.setOnClickListener(this::onAceptarModificarFechaSalida);
        buttonModificarFechaSalida = (Button) findViewById(R.id.button_aceptar_modificar_reserva);
        buttonModificarFechaSalida.setOnClickListener(this::onAceptar);

    }

    private void onAceptar(View view) {
        newReserva = old;
        handler.updateReservas(old, newReserva);
        reservas.add(newReserva);
        adapter.notifyDataSetChanged();
        getContext().startActivity(new Intent(getContext(), ClientesActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onAceptarModificarFechaSalida(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.MyDatePickerStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        old.setFechaSalida(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @SuppressLint("NewApi")
    private void onAceptarModificarFechaEntrada(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.MyDatePickerStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        old.setFechaEntrada(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}
