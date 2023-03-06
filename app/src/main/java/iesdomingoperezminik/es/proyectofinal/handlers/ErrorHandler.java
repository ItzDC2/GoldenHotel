package iesdomingoperezminik.es.proyectofinal.handlers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import iesdomingoperezminik.es.proyectofinal.R;
import iesdomingoperezminik.es.proyectofinal.model.Error;

public class ErrorHandler {

    public static void mostrarError(@NonNull Error e, TextView errorView, CardView errorCardView) {
        switch(e) {
            case EMPTY_NAME:
                errorView.setText(R.string.empty_name);
                break;
            case EMPTY_SURNAME:
                errorView.setText(R.string.empty_surname);
                break;
            case EMPTY_EMAIL:
                errorView.setText(R.string.empty_email);
                break;
            case EMPTY_PASSWORD:
                errorView.setText(R.string.empty_password);
                break;
            case ALREADY_REGISTERED:
                errorView.setText(R.string.email_already_in_use);
                break;
            case INVALID_EMAIL:
                errorView.setText(R.string.invalid_email);
                break;
            case PASSWORD_MISMATCH:
                errorView.setText(R.string.password_mismatch);
                break;
            case PASSWORD_LENGTH:
                errorView.setText(R.string.password_length);
                break;
            case INVALID_PASSWORD:
                errorView.setText(R.string.invalid_password);
                break;
            case EMAIL_NOT_FOUND:
                errorView.setText(R.string.email_not_found);
                break;
        }
        errorCardView.setVisibility(View.VISIBLE);
    }

}
