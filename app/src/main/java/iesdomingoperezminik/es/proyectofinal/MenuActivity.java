package iesdomingoperezminik.es.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private static SQLHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav = (NavigationView) findViewById(R.id.nav_main_view);
        MenuActivity.handler = new SQLHandler(this);

    }

    @Override
    protected void onResume() { super.onResume(); }

    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    public void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    protected void clickLogin() {
        startActivity(new Intent(MenuActivity.this, LoginActivity.class) {{
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }});
    }

    protected void clickClientes() {
        startActivity(new Intent(this, ClientesActivity.class) {{
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }});
    }

    protected void clickReservas() {
        startActivity(new Intent(this, ReservasActivity.class) {{
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }});
    }

    protected void clickLogout() {
        SessionHandler.setLogged(false);
        startActivity(new Intent(this, MainActivity.class) {{
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }});
    }

    protected void clickSettings() {
        startActivity(new Intent(this, SettingsActivity.class) {{
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }});
    }

    public static SQLHandler getSQLHandler() {
        return handler;
    }

}