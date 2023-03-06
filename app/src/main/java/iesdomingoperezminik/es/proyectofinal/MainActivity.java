package iesdomingoperezminik.es.proyectofinal;

import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import iesdomingoperezminik.es.proyectofinal.handlers.SQLHandler;
import iesdomingoperezminik.es.proyectofinal.handlers.SessionHandler;

public class MainActivity extends MenuActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private ImageButton drawerButton;

    private SQLHandler handler;
    private TextView nameSurname;
    private TextView email;

    @SuppressLint({"NonConstantResourceId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerButton = (ImageButton) findViewById(R.id.more_button);
        drawerButton.setOnClickListener((i) -> { super.openDrawer(drawerLayout); });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav = (NavigationView) findViewById(R.id.nav_main_view);

        handler = new SQLHandler(this);

    }

    @SuppressLint({"ResourceType", "NonConstantResourceId"})
    @Override
    protected void onResume() {
        super.onResume();
        initCarroussells();

        nameSurname = (TextView) nav.getHeaderView(0).findViewById(R.id.nombre_apellidos);
        email = (TextView) nav.getHeaderView(0).findViewById(R.id.email_usuario);

        if(!SessionHandler.isLogged()) {
            nav.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
            nav.getMenu().findItem(R.id.nav_item_clientes).setVisible(false);
            nav.getMenu().findItem(R.id.nav_item_reservas).setVisible(false);
        } else {
            nav.getMenu().findItem(R.id.nav_item_login).setVisible(false);
            nav.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
            nav.getMenu().findItem(R.id.nav_item_clientes).setVisible(true);
            nav.getMenu().findItem(R.id.nav_item_reservas).setVisible(true);

            if(getIntent().getStringExtra("name_surname") != null && getIntent().getStringExtra("email_usuario") != null) {
                nameSurname.setText(handler.existeAdmin(getIntent().getStringExtra("email_usuario")) ? "(ADMIN) " + getIntent().getStringExtra("name_surname") : getIntent().getStringExtra("name_surname"));
                email.setText(getIntent().getStringExtra("email_usuario"));
            }
        }

        nav.setNavigationItemSelectedListener(i -> {
            switch (i.getItemId()) {
                case R.id.nav_item_login:
                    super.clickLogin();
                    break;
                case R.id.nav_item_clientes:
                    super.clickClientes();
                    break;
                case R.id.nav_item_reservas:
                    super.clickReservas();
                    break;
                case R.id.nav_item_logout:
                    super.clickLogout();
                    nameSurname.setText(getString(R.string.default_name));
                    email.setText(getString(R.string.default_email));
                    break;
                case R.id.nav_item_settings:
                    super.clickSettings();
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });

    }

    private void initCarroussells() {

        ImageSlider habitacionesSlider = findViewById(R.id.habitaciones_imageslider);
        ImageSlider spaSlider = findViewById(R.id.spa_imageslider);
        ImageSlider restauranteSlider = findViewById(R.id.restaurante_imageslider);

        List<SlideModel> habitacionesSlideModels = new ArrayList<>() {{
            add(new SlideModel(R.drawable.habitacion_hotel1, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.habitacion_hotel2, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.habitacion_hotel3, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.habitacion_hotel4, ScaleTypes.FIT));
        }};

        List<SlideModel> spaSlideModels = new ArrayList<>() {{
            add(new SlideModel(R.drawable.spa_hotel1, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.spa_hotel2, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.spa_hotel3, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.spa_hotel4, ScaleTypes.FIT));
        }};

        List<SlideModel> restauranteSlideModels = new ArrayList<>() {{
            add(new SlideModel(R.drawable.restaurante_hotel1, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.restaurante_hotel2, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.restaurante_hotel3, ScaleTypes.FIT));
            add(new SlideModel(R.drawable.restaurante_hotel4, ScaleTypes.FIT));
        }};

        habitacionesSlider.setImageList(habitacionesSlideModels);
        spaSlider.setImageList(spaSlideModels);
        restauranteSlider.setImageList(restauranteSlideModels);
    }

}