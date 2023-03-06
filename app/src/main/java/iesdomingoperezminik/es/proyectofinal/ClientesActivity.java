package iesdomingoperezminik.es.proyectofinal;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import iesdomingoperezminik.es.proyectofinal.adapters.ClientesAdapter;
import iesdomingoperezminik.es.proyectofinal.dialogs.EditClienteDialog;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;
import iesdomingoperezminik.es.proyectofinal.model.Cliente;

public class ClientesActivity extends MenuActivity {

    private SQLHandler handler;
    private ListView clientesView;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ClientesAdapter adapter;

    private ArrayList<String> admins;

    private TextView clientesNameAndSurnameRow;
    private TextView clientesEmailRow;
    private TextView clientesReservasRow;
    private Button volverButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new SQLHandler(this);
        admins = handler.getAdmins();
        clientes = handler.getClientes();

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.clientes_options_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.option_editar_cliente:
                editarCliente(i.position);
                break;
            case R.id.option_eliminar_cliente:
                eliminarCliente(i.position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void addCliente() {
        Cliente c;

        handler.insertarCliente((c = new Cliente(getString(R.string.default_create_clientes_name), getString(R.string.default_create_clientes_surname),
                getString(R.string.default_create_clientes_email), getString(R.string.default_create_clientes_password))));

        clientes.add(c);
        adapter.notifyDataSetChanged();
    }

    private void editarCliente(int pos) {
        EditClienteDialog dialog = new EditClienteDialog(this, clientes.get(pos), clientes, adapter);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.show();
    }

    @SuppressLint("NewApi")
    private void eliminarCliente(int pos) {

        Cliente c = clientes.get(pos);
        ArrayList<Cliente> clientesNew = new ArrayList<>();

        new AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("¿Seguro que deseas eliminar este cliente?")
            .setIcon(android.R.drawable.stat_sys_warning)
            .setPositiveButton("Aceptar", (d, i) -> {
                clientesNew.addAll(handler.getClientes());
                clientesNew.removeIf(cl -> handler.getAdmins().contains(cl.getEmail()));
                clientes.clear();
                clientes.addAll(clientesNew);
                handler.deleteCliente(c);
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            })
            .setNegativeButton("Cancelar", (d, i) -> {d.dismiss();}).show();

    }

    @SuppressLint("NewApi")
    private void init() {
        setSupportActionBar(findViewById(R.id.admin_clientes_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton back = (ImageButton) findViewById(R.id.back_client_button);
        ImageButton add = (ImageButton) findViewById(R.id.add_client_button);

        if(!admins.contains(SessionHandler.getSessionEmail())) {
            setContentView(R.layout.activity_clientes);

            add.setVisibility(View.GONE);
            clientesNameAndSurnameRow = (TextView) findViewById(R.id.cliente_name_surname_row);
            clientesEmailRow = (TextView) findViewById(R.id.cilente_email_row);
            clientesReservasRow = (TextView) findViewById(R.id.cliente_reserva_row);
            volverButton = (Button) findViewById(R.id.volverButton);

            clientesNameAndSurnameRow.setText(handler.getNombreYApellidos(SessionHandler.getSessionEmail()));
            clientesEmailRow.setText(SessionHandler.getSessionEmail());
            clientesReservasRow.setText(handler.tieneReservaActiva(handler.getId(SessionHandler.getSessionEmail())) ? "Tienes " : "No tienes " + "reservas activas.");
            volverButton.setOnClickListener(l -> finish());

        } else {
            setContentView(R.layout.admin_activity_clientes);

            TextView clientesNotFound = (TextView) findViewById(R.id.clientes_not_found_view);
            ListView clientesView;

            clientes = handler.getClientes();

            back.setOnClickListener(l -> finish());
            add.setOnClickListener(l -> {
                addCliente();
                adapter.notifyDataSetChanged();
            });

            clientesView = (ListView) findViewById(R.id.clientes_view);

            adapter = new ClientesAdapter(this, clientes);
            clientes.removeIf(i -> handler.getAdmins().contains(i.getEmail()));

            if(clientes.size() == 0) {
                clientesView.setVisibility(View.GONE);
                clientesNotFound.setVisibility(View.VISIBLE);
            }

            registerForContextMenu(clientesView);
            clientesView.setAdapter(adapter);

        }
    }

}