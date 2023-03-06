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
import iesdomingoperezminik.es.proyectofinal.ReservasActivity;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.model.Reserva;

public class ReservasAdapter extends ArrayAdapter<Reserva> {

    private List<Reserva> reservas;
    private SQLHandler handler;

    public ReservasAdapter(@NonNull Context context, List<Reserva> reservas) {
        super(context, R.layout.reservas_row, reservas);
        handler = new SQLHandler(getContext());
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservas_row, null);

        Reserva r = reservas.get(pos);

        TextView nameAndSurname = (TextView) convertView.findViewById(R.id.admin_reservas_name_surname_row);
        TextView email = (TextView) convertView.findViewById(R.id.admin_reservas_email_row);
        TextView numHabitacion = (TextView) convertView.findViewById(R.id.admin_reservas_num_habitacion_row);
        TextView enterDate = (TextView) convertView.findViewById(R.id.admin_reservas_enter_date_row);
        TextView leaveDate = (TextView) convertView.findViewById(R.id.admin_reservas_leave_date_row);

        nameAndSurname.setText("DEFAULT TEXT");
        email.setText(handler.getEmail(r.getId_cliente()));
        numHabitacion.setText(String.valueOf(r.getNumHabitacion()));
        enterDate.setText(String.valueOf(r.getFechaEntrada()));
        leaveDate.setText(String.valueOf(r.getFechaSalida()));

        return convertView;
    }

}
