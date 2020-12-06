package com.juancoche.mydogv3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.adapters.PerreteAdapterMain;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterTusMascotas;
import com.juancoche.mydogv3.adapters.TusMascotasAdapter;

import java.util.ArrayList;

public class MisMascotasFragment extends Fragment {

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private FirebaseUser user;
    private ImageView imageViewProfile;
    private TextView textViewName;
    private TusMascotasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_mis_mascotas, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        imageViewProfile = view.findViewById(R.id.profile_image);
        textViewName = view.findViewById(R.id.name);

        loadHeader(view);
        loadPets(view);

        return view;
    }

    private void loadHeader(View v) {
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(v)
                        .asBitmap()
                        .load(user.getPhotoUrl())
                        .into(imageViewProfile);
            }
            if (user.getDisplayName() != null)
                textViewName.setText(user.getDisplayName());
        }
    }

    private void loadPets(final View view) {
        Query query = FirebaseFirestore.getInstance()
                .collection("users").document(user.getEmail())
                .collection("pets");

        final FirestoreRecyclerOptions<Perrete> options
                = new FirestoreRecyclerOptions.Builder<Perrete>()
                .setQuery(query, Perrete.class)
                .build();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMisMascotas);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TusMascotasAdapter(options);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPetProfile(new MiMascotaFragment(),
                        adapter.getItem(recyclerView.getChildAdapterPosition(v)).getNombre());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void goToPetProfile(Fragment fragment, String nombre) {
        Bundle i = new Bundle();
        i.putString("nombre", nombre);
        fragment.setArguments(i);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    @Override public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}
