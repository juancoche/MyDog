package com.juancoche.mydogv3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.juancoche.mydogv3.fragments.EditMascotaFragment;
import com.juancoche.mydogv3.fragments.MainFragment;
import com.juancoche.mydogv3.fragments.MisMascotasFragment;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.fragments.UtilidadesFragment;

public class MainActivity extends AppCompatActivity implements EditMascotaFragment.OnFragmentInteractionListener {

    private boolean viewIsAtHome;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        addFragment(new MainFragment());
                        viewIsAtHome = true;
                        break;
                    case R.id.pets:
                        addFragment(new MisMascotasFragment());
                        viewIsAtHome = false;
                        break;
                    case R.id.utilidades:
                        addFragment(new UtilidadesFragment());
                        viewIsAtHome = false;
                }
                return true;
            }
        });
        bottomNavigation.setSelectedItemId(R.id.home);
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!viewIsAtHome) { //if the current view is not the News fragment
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
