package iesdomingoperezminik.es.proyectofinal;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import iesdomingoperezminik.es.proyectofinal.handlers.ErrorHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.TextUtils;
import iesdomingoperezminik.es.proyectofinal.model.Cliente;
import iesdomingoperezminik.es.proyectofinal.model.Error;

public class LoginActivity extends MenuActivity {

    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;
    private TextView registerView;
    private SQLHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new SQLHandler(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerView = (TextView) findViewById(R.id.not_registered_view);
        registerView.setOnClickListener(i -> startActivity(new Intent(this, RegisterActivity.class) {{
            setFlags(FLAG_ACTIVITY_NEW_TASK);
        }}));

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

    }

    public void validarInputs(View view) {
        boolean res = false;
        Error error = null;
        TextView errorView;
        CardView errorCardView;

        String loginEmailText = loginEmail.getText().toString();
        String loginPasswordText = loginPassword.getText().toString();

        errorView = (TextView) findViewById(R.id.login_error_view);
        errorCardView = (CardView) findViewById(R.id.login_error_card_view);

        if(loginEmailText.length() == 0)
            error = Error.EMPTY_EMAIL;
        else if(!handler.existeCliente(loginEmailText))
            error = Error.EMAIL_NOT_FOUND;
        else if(!Patterns.EMAIL_ADDRESS.matcher(loginEmailText).matches())
            error = Error.INVALID_EMAIL;
        else if(loginPasswordText.length() == 0)
            error = Error.EMPTY_PASSWORD;
        else if(!handler.getPassword(loginEmailText).equals(SessionHandler.MD_5(loginPasswordText)))
            error = Error.INVALID_PASSWORD;
        else {
            errorView.setText("");
            errorCardView.setVisibility(View.GONE);
            res = true;
            SessionHandler.setLogged(true);
            SessionHandler.setSessionEmail(loginEmailText);
            TextUtils.makeSnak(findViewById(android.R.id.content), getString(R.string.login_successfull), 2500);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class) {{
                    putExtra("name_surname", handler.getNombreYApellidos(loginEmailText));
                    putExtra("email_usuario", SessionHandler.getSessionEmail());
                }});
                finish();
            },2500);
        }

        if(!res)
            ErrorHandler.mostrarError(error, errorView, errorCardView);
    }

}