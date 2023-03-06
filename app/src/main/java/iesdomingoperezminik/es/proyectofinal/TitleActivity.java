package iesdomingoperezminik.es.proyectofinal;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Splash_Theme);
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ProyectoFinal);
        setContentView(R.layout.activity_title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toolbar tb = (Toolbar) findViewById(R.id.materialToolbar);
        tb.setTitle("");
        setSupportActionBar(tb);

    }

    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.acercaDe)
            showHelp();
        return true;
    }

    private void showHelp() {
        AcercaDeDialog dialog = new AcercaDeDialog();
        dialog.show(getSupportFragmentManager(), "");
    }
}