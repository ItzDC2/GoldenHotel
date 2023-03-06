package iesdomingoperezminik.es.proyectofinal.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import iesdomingoperezminik.es.proyectofinal.R;
import iesdomingoperezminik.es.proyectofinal.adapters.ClientesAdapter;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;
import iesdomingoperezminik.es.proyectofinal.model.Cliente;

public class EditClienteDialog extends Dialog {

    private SQLHandler handler;
    private Button buttonAceptar;
    private Cliente old;
    private ArrayList<Cliente> clientes;
    private ClientesAdapter adapter;

    public EditClienteDialog(@NonNull Context context, Cliente c, ArrayList<Cliente> clientes, ClientesAdapter adapter) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_clientes_form_dialog);
        this.old = c;
        this.clientes = clientes;
        this.adapter = adapter;

        handler = new SQLHandler(context);

        buttonAceptar = (Button) findViewById(R.id.button_aceptar_modificar_cliente);
        buttonAceptar.setOnClickListener(this::onAceptar);

    }

    @SuppressLint("NewApi")
    private void onAceptar(View view) {

        Cliente nuevo;

        TextInputEditText name = (TextInputEditText) findViewById(R.id.admin_clientes_name);
        TextInputEditText surname = (TextInputEditText) findViewById(R.id.admin_clientes_surname);
        TextInputEditText email  = (TextInputEditText) findViewById(R.id.admin_clientes_email);
        TextInputEditText password = (TextInputEditText) findViewById(R.id.admin_clientes_password);
        CardView errorCardView = (CardView) findViewById(R.id.admin_clientes_error_card_view);
        TextView errorView = (TextView) findViewById(R.id.admin_clientes_error_view);

        String nombre = String.valueOf(name.getText());
        String apellidos = String.valueOf(surname.getText());
        String emailText = String.valueOf(email.getText());
        String passwordText = SessionHandler.MD_5(String.valueOf(password.getText()));

        errorCardView.setVisibility(View.VISIBLE);
        errorCardView.setBackgroundColor(Color.TRANSPARENT);

        if(emailText.length() > 0 && !Patterns.EMAIL_ADDRESS.matcher(String.valueOf(email.getText())).matches()) {
            errorCardView.setBackgroundColor(getContext().getColor(R.color.red));
            errorView.setText(R.string.invalid_email);
        } else {
            handler.updateCliente(old, (nuevo = new Cliente(handler.getId(old.getEmail()),
                    nombre.length() > 0 ? nombre : old.getNombre(), apellidos.length() > 0 ? apellidos : old.getApellidos(),
                    emailText.length() > 0 ? emailText : old.getEmail(),
                    passwordText.length() > 0 ? passwordText : old.getContraseña())));

            errorCardView.setBackgroundColor(getContext().getColor(R.color.green));
            errorView.setText(R.string.update_successfull);

            errorCardView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

            clientes.set(clientes.indexOf(old), nuevo);
            new Handler().postDelayed(() -> {
                adapter.notifyDataSetChanged();
                dismiss();
            },2500);
        }
    }
}