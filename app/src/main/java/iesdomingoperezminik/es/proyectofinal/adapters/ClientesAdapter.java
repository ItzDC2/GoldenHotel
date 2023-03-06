package iesdomingoperezminik.es.proyectofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import iesdomingoperezminik.es.proyectofinal.R;
import iesdomingoperezminik.es.proyectofinal.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {

    private List<Cliente> clientes;

    public ClientesAdapter(@NonNull Context context, List<Cliente> clientes) {
        super(context, R.layout.clientes_row, clientes);
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clientes_row, null);

        Cliente c = clientes.get(pos);

        TextView nameAndSurname = (TextView) convertView.findViewById(R.id.admin_clientes_name_surname_row);
        TextView email = (TextView) convertView.findViewById(R.id.admin_clientes_email_row);
        TextView passwd = (TextView) convertView.findViewById(R.id.admin_clientes_password_row);
        TextView reserva = (TextView) convertView.findViewById(R.id.admin_clientes_reserva_row);

        nameAndSurname.setText(String.format("%s %s", c.getNombre(), c.getApellidos()));
        email.setText(String.format("%s", c.getEmail()));
        passwd.setText(String.format("%s", c.getContraseña()));
        reserva.setText(c.isReservaActiva() ? "Tiene" : "No tiene" + " reservas activas.");

        return convertView;
    }

}
