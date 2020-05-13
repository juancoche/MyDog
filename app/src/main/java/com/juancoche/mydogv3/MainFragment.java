package com.juancoche.mydogv3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainFragment extends Fragment {

    //Provisonal para probar cambios hasta configurar BD

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private ArrayList<Integer> fotos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        llenarFotos();

        //Inflar RecyclerView con últimas fotos
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerViewFotos);
        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerViewAdapterMainUltimasFotos adapter2 = new RecyclerViewAdapterMainUltimasFotos(fotos, view.getContext());
        recyclerView2.setAdapter(adapter2);

        llenarPerretes();

        //Inflar RecyclerView con mascotas
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterMainTusMascotas adapter = new RecyclerViewAdapterMainTusMascotas(perretes, view.getContext());
        recyclerView.setAdapter(adapter);


        FloatingActionButton floatingActionButton =
                (FloatingActionButton) view.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
            }
        });

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

    //Llenamos la lista de imagenes de prueba
    private void llenarFotos() {

        fotos.add(R.drawable.perrouno);
        fotos.add(R.drawable.perrodos);
        fotos.add(R.drawable.perrotres);
        fotos.add(R.drawable.perrocuatro);
        fotos.add(R.drawable.perrocinco);
        fotos.add(R.drawable.perroseis);

        Collections.shuffle(fotos);

    }

}
