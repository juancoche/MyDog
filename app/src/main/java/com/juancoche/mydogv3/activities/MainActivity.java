package com.juancoche.mydogv3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.adapters.PagerAdapter;
import com.juancoche.mydogv3.fragments.MainFragment;
import com.juancoche.mydogv3.fragments.MisMascotasFragment;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.activities.login.LoginActivity;
import com.juancoche.mydogv3.fragments.NewMainFragment;
import com.juancoche.mydogv3.fragments.NewMisMascotasFragment;

public class MainActivity extends AppCompatActivity {

    private boolean viewIsAtHome;
    private FirebaseUser user;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private MenuItem prevBottomSelected = null;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //viewPager = findViewById(R.id.viewPager);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //setUpViewPager(getPagerAdapter());
        //setUpBottomNavigationBar();


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
                        // Log Out provisional
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                        Toast.makeText(getBaseContext(), "Has cerrado sesión", Toast.LENGTH_LONG).show();

                        /*GoogleSignInClient mGoogleSignInClient;
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(getBaseContext(), gso);
                        // Para que al cerrar sesión y volver a login obligue a seleccionar la cuenta de google
                        mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this,
                                new OnCompleteListener<Void>() {  //signout Google
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseAuth.getInstance().signOut(); //signout firebase
                                        Intent setupIntent = new Intent(getBaseContext(), LoginActivity.class);
                                        Toast.makeText(getBaseContext(), "Has cerrado sesión", Toast.LENGTH_LONG).show(); //if u want to show some text
                                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(setupIntent);
                                        finish();
                                    }
                                });*/

                        //addFragment(fragment1);
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
    /*
    public PagerAdapter getPagerAdapter () {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewMainFragment());
        adapter.addFragment(new NewMisMascotasFragment());
        return adapter;
    }

    public void setUpViewPager (PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageSelected(int position) {
                if(prevBottomSelected == null) {
                    bottomNavigation.getMenu().getItem(0).setChecked(false);
                } else {
                    prevBottomSelected.setChecked(false);
                }
                bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevBottomSelected = bottomNavigation.getMenu().getItem(position);
            }
        });
    }

    public void setUpBottomNavigationBar() {
        bottomNavigation.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //addFragment(new MainFragment());
                        viewPager.setCurrentItem(0);
                        viewIsAtHome = true;
                        break;
                    case R.id.pets:
                        //addFragment(new MisMascotasFragment());
                        viewPager.setCurrentItem(1);
                        viewIsAtHome = false;
                        break;
                    case R.id.buscar:
                        viewPager.setCurrentItem(2);
                        // Log Out provisional
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        //addFragment(fragment1);
                        //viewIsAtHome = false;
                }
                return false;
            }
        });
    }*/

}
