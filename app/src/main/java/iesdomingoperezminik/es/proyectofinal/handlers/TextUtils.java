package iesdomingoperezminik.es.proyectofinal.handlers;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import iesdomingoperezminik.es.proyectofinal.R;

public class TextUtils {

    public static void makeSnak(View view, String text, int duration) {
        Snackbar snack = Snackbar.make(view, text, duration);
        snack.setBackgroundTint(Color.parseColor("#DE9B2C"));
        snack.setTextColor(Color.WHITE);
        snack.show();
    }

}
