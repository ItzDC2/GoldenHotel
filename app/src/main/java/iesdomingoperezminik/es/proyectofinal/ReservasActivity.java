package iesdomingoperezminik.es.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import iesdomingoperezminik.es.proyectofinal.adapters.ReservasAdapter;
import iesdomingoperezminik.es.proyectofinal.dialogs.EditReservaDialog;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;
import iesdomingoperezminik.es.proyectofinal.model.Reserva;

public class ReservasActivity extends MenuActivity {

    private SQLHandler handler;
    private ListView reservasView;
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ReservasAdapter adapter;

    private ArrayList<String> admins;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        handler = new SQLHandler(this);
        admins = handler.getAdmins();
        reservas = handler.getReservas();

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.clientes_options_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.option_editar_cliente:
                editarReserva(i.position);
                break;
            case R.id.option_eliminar_cliente:
                eliminarReserva(i.position);
                break;

        }
        return super.onContextItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        if(!admins.contains(SessionHandler.getSessionEmail())) {
            setContentView(R.layout.activity_reservas);

        } else {
            setContentView(R.layout.admin_activity_reservas);
            setSupportActionBar(findViewById(R.id.admin_reservas_toolbar));
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            ImageButton back = (ImageButton) findViewById(R.id.back_client_button);
            ImageButton add = (ImageButton) findViewById(R.id.add_reservas_button);

            TextView reservasNotFound = (TextView) findViewById(R.id.reservas_not_found_view);
            ListView reservasView;

            reservas = handler.getReservas();

            back.setOnClickListener(l -> finish());
            add.setOnClickListener(l -> addReserva());

            reservasView = (ListView) findViewById(R.id.reservas_view);

            adapter = new ReservasAdapter(this, reservas);

            if(reservas.size() == 0) {
                reservasView.setVisibility(View.GONE);
                reservasNotFound.setVisibility(View.VISIBLE);
            }

            registerForContextMenu(reservasView);
            reservasView.setAdapter(adapter);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void editarReserva(int pos) {

        Reserva r = reservas.get(pos);
        EditReservaDialog dialog = new EditReservaDialog(this, reservas.get(pos), reservas, adapter);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.show();

    }

    private void eliminarReserva(int pos) {

        Reserva r = reservas.get(pos);

        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Seguro que deseas eliminar esta reserva?")
                .setIcon(android.R.drawable.stat_sys_warning)
                .setPositiveButton("Aceptar", (d, i) -> {
                    handler.deleteReserva(r);
                    reservas.clear();
                    reservas.addAll(handler.getReservas());
                    adapter.notifyDataSetChanged();
                }).setNegativeButton("Cancelar", (d, i) -> { d.dismiss(); }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addReserva() {
        Reserva r;
        handler.insertarReserva((r = new Reserva(0, -1, getString(R.string.default_create_reservas_name),
                getString(R.string.default_create_reservas_surname),
                getString(R.string.default_create_clientes_email), Integer.parseInt(getString(R.string.admin_reservas_default_room_row)),
                        LocalDate.now(), LocalDate.now())));
        reservas.add(r);
        adapter.notifyDataSetChanged();
    }

}