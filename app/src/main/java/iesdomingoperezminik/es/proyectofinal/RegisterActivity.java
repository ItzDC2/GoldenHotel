package iesdomingoperezminik.es.proyectofinal;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class RegisterActivity extends MenuActivity {

    private TextInputEditText registerName;
    private TextInputEditText registerSurname;
    private TextInputEditText registerEmail;
    private TextInputEditText registerPassword;
    private TextInputEditText registerConfirmPassword;
    private SQLHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerName = (TextInputEditText) findViewById(R.id.register_name);
        registerSurname = (TextInputEditText) findViewById(R.id.register_surname);
        registerEmail = (TextInputEditText) findViewById(R.id.register_email);
        registerPassword = (TextInputEditText) findViewById(R.id.register_password);
        registerConfirmPassword = (TextInputEditText) findViewById(R.id.register_confirm_password);

        TextView loginView = (TextView) findViewById(R.id.register_already_registered);
        loginView.setOnClickListener(i -> startActivity(new Intent(this, LoginActivity.class) {{
            setFlags(FLAG_ACTIVITY_NEW_TASK);
        }}));

        handler = new SQLHandler(this);

    }

    public void validarInputs(View view) {
        boolean res = false;
        Error error = null;
        TextView errorView;
        CardView errorCardView;

        String registerNameText = registerName.getText().toString();
        String registerSurnameText = registerSurname.getText().toString();
        String registerEmailText = registerEmail.getText().toString();
        String registerPasswordText = registerPassword.getText().toString();
        String registerConfirmPasswordText = registerConfirmPassword.getText().toString();

        errorView = (TextView) findViewById(R.id.login_error_view);
        errorCardView = (CardView) findViewById(R.id.login_card_view);

        if(registerNameText.length() == 0)
            error = Error.EMPTY_NAME;
        else if(registerSurnameText.length() == 0)
            error = Error.EMPTY_SURNAME;
        else if(registerEmailText.length() == 0)
            error = Error.EMPTY_EMAIL;
        else if(handler.esClienteDuplicado(registerEmailText))
            error = Error.ALREADY_REGISTERED;
        else if(!Patterns.EMAIL_ADDRESS.matcher(registerEmailText).matches())
            error = Error.INVALID_EMAIL;
        else if(registerPasswordText.length() == 0)
            error = Error.EMPTY_PASSWORD;
        else if(!registerPasswordText.equals(registerConfirmPasswordText))
            error = Error.PASSWORD_MISMATCH;
        else if(registerPasswordText.length() <= 5)
            error = Error.PASSWORD_LENGTH;

        else {
            errorView.setText("");
            errorCardView.setVisibility(View.GONE);
            res = true;
            handler.insertarCliente(new Cliente(0, registerNameText, registerSurnameText, registerEmailText, SessionHandler.MD_5(registerPasswordText)));
            SessionHandler.setLogged(true);
            SessionHandler.setSessionEmail(registerEmailText);

            TextUtils.makeSnak(findViewById(android.R.id.content), getString(R.string.register_successfull), 2500);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class) {{
                    putExtra("name_surname", registerNameText + " " + registerSurnameText);
                    putExtra("email_usuario", SessionHandler.getSessionEmail());
                }});
                finish();
            },2500);
        }

        if(!res)
            ErrorHandler.mostrarError(error, errorView, errorCardView);
    }

}