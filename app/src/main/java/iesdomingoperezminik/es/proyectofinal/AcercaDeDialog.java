package iesdomingoperezminik.es.proyectofinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AcercaDeDialog extends AppCompatDialogFragment {

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Acerca de")
                .setMessage("Esta aplicación ha sido creada en Android Studio programando en Java y su autor es Dónovan Castro Fariña estudiante de 2º de DAM.\n" +
                        "Proyecto final de la asignatura PGL - Programación Multimedia y Dispositivos móviles")
                .setPositiveButton("Aceptar", (d, i) -> {});
        return builder.create();
    }

}