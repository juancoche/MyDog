package com.juancoche.mydogv3.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.R;

public class UtilidadesFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private ImageView imageViewProfile;
    private TextView textViewName;
    private ImageButton nearVet, nearShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_utilidades, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        loadElements(view);

        return view;
    }

    private void loadElements(View view) {
        imageViewProfile = view.findViewById(R.id.profile_image);
        textViewName = view.findViewById(R.id.name);
        nearVet = view.findViewById(R.id.nearVet);
        nearShop = view.findViewById(R.id.nearShop);
        loadHeader(view);
        nearVet.setOnClickListener(this);
        nearShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nearVet:
                nearVet(v);
                break;
            case R.id.nearShop:
                nearShop(v);
        }
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

    public void nearVet(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=veterinario");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void nearShop(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=tienda de mascotas");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}