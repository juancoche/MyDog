package com.juancoche.mydogv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private ArrayList<Perrete> perretes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        llenarPerretes();

        LinearLayoutManager layoutManager =  new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(perretes, view.getContext());
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

        perretes.add(new Perrete(R.drawable.perrouno, "Perro1"));
        perretes.add(new Perrete(R.drawable.perrodos, "Perro2"));
        perretes.add(new Perrete(R.drawable.perrotres, "Perro3"));
        perretes.add(new Perrete(R.drawable.perrocuatro, "Perro4"));
        perretes.add(new Perrete(R.drawable.perrocinco, "Perro5"));
        perretes.add(new Perrete(R.drawable.perroseis, "Perro6"));

        //initRecyclerView();

    }

    private void initRecyclerView() {



    }




}
