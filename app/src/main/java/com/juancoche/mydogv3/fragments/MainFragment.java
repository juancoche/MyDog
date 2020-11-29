package com.juancoche.mydogv3.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.activities.MainActivity;
import com.juancoche.mydogv3.activities.login.LoginActivity;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterMainTusMascotas;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterMainUltimasFotos;

import java.util.ArrayList;
import java.util.Collections;

public class MainFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    //Provisonal para probar cambios hasta configurar BD

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private FirebaseUser user;
    private ImageView imageViewProfile;
    private TextView textViewName;
    private ImageButton options;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        imageViewProfile = view.findViewById(R.id.imageView_profile);
        textViewName = view.findViewById(R.id.name);
        options = view.findViewById(R.id.ajustes);
        options.setOnClickListener(this);

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(view)
                        .asBitmap()
                        .load(user.getPhotoUrl())
                        .into(imageViewProfile);
            } else {
                imageViewProfile.setBackgroundResource(R.mipmap.ic_default_profile_img);
            }
            if (user.getDisplayName().equals(""))
                textViewName.setText("Nombre de usuario");
            else
                textViewName.setText(user.getDisplayName());
        }





        llenarPerretes();

        //Inflar RecyclerView con mascotas
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterMainTusMascotas adapter = new RecyclerViewAdapterMainTusMascotas(perretes, view.getContext());
        recyclerView.setAdapter(adapter);


//        FloatingActionButton floatingActionButton =
//                (FloatingActionButton) view.findViewById(R.id.floating_action_button);
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle the click.
//            }
//        });

        return view;
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = this.getMenuInflater();
//        inflater.inflate(R.menu.settings_menu, menu);
//    }

    //Llenamos la lista de perretes de prueba
    private void llenarPerretes() {

        perretes.add(new Perrete(R.drawable.perrouno, "Perro1", "Mestizo"));
        perretes.add(new Perrete(R.drawable.perrodos, "Perro2", "Galgo"));
        perretes.add(new Perrete(R.drawable.perrotres, "Perro3", "Podenco"));
        perretes.add(new Perrete(R.drawable.perrocuatro, "Perro4", "BullDog"));
        perretes.add(new Perrete(R.drawable.perrocinco, "Perro5", "YorkShire"));
        perretes.add(new Perrete(R.drawable.perroseis, "Perro6", "Caniche"));

    }


    @Override
    public void onClick(View v) {
        showPopupMenu(v);
    }

    private void showPopupMenu (View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.settings_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setMessage("¿Deseas cerrar sesión?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                Toast.makeText(getContext(), "Has cerrado sesión", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            default:
                return false;
        }
    }
}
