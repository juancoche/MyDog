package com.juancoche.mydogv3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterTusMascotas;

import java.util.ArrayList;

public class MisMascotasFragment extends Fragment {

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private FirebaseUser user;
    private ImageView imageViewProfile;
    private TextView textViewName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_mis_mascotas, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        imageViewProfile = view.findViewById(R.id.profile_image);
        textViewName = view.findViewById(R.id.name);

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMisMascotas);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterTusMascotas adapter = new RecyclerViewAdapterTusMascotas(perretes, view.getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }


    //Llenamos la lista de perretes de prueba
    private void llenarPerretes() {

        perretes.add(new Perrete(R.drawable.perrouno, "Perro1", "Mestizo"));
        perretes.add(new Perrete(R.drawable.perrodos, "Perro2", "Galgo"));
        perretes.add(new Perrete(R.drawable.perrotres, "Perro3", "Podenco"));
        perretes.add(new Perrete(R.drawable.perrocuatro, "Perro4", "BullDog"));
        perretes.add(new Perrete(R.drawable.perrocinco, "Perro5", "YorkShire"));
        perretes.add(new Perrete(R.drawable.perroseis, "Perro6", "Caniche"));

    }
}
