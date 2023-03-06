package iesdomingoperezminik.es.proyectofinal;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import iesdomingoperezminik.es.proyectofinal.fragments.BatteryInfo;
import iesdomingoperezminik.es.proyectofinal.fragments.GrabadoraFragment;
import iesdomingoperezminik.es.proyectofinal.fragments.LightSensor;
import iesdomingoperezminik.es.proyectofinal.fragments.MetricsFragment;
import iesdomingoperezminik.es.proyectofinal.fragments.NetworkFragment;

public class SettingsActivity extends MenuActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragment, new MetricsFragment()).commit();

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_metrics));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_grabadora));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_network));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_luz));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_bateria));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment, new MetricsFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment, new GrabadoraFragment()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment, new NetworkFragment()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment, new LightSensor()).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment, new BatteryInfo()).commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
}