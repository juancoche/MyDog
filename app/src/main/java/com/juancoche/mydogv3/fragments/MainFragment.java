package com.juancoche.mydogv3.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.activities.MainActivity;
import com.juancoche.mydogv3.activities.login.LoginActivity;
import com.juancoche.mydogv3.activities.login.SignUpActivity;
import com.juancoche.mydogv3.adapters.PerreteAdapterMain;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterMainTusMascotas;
import com.juancoche.mydogv3.adapters.RecyclerViewAdapterMainUltimasFotos;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    //Provisonal para probar cambios hasta configurar BD

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private FirebaseUser user;
    private ImageView imageViewProfile;
    private TextView textViewName;
    private ImageButton options;
    private FirebaseFirestore db;
    private StorageReference reference;
    private HashMap<String,String> userMap;
    private PerreteAdapterMain adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        imageViewProfile = view.findViewById(R.id.imageView_profile);
        textViewName = view.findViewById(R.id.name);
        options = view.findViewById(R.id.ajustes);
        options.setOnClickListener(this);

        loadHeader(view);
        loadPets(view);


        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
//                                imagenCabecera.setImageBitmap(r.getBitmap());
                                uploadHeaderImg(r.getUri());
                                imageViewProfile.setImageURI(r.getUri());
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity().getSupportFragmentManager());
            }
        });


       /* Perrete perrete = new Perrete();
        perrete.setNombre("Luna");
        perrete.setRaza("mestizo");
        perrete.setFnac("10/10/16");
        perrete.setGenero("hembra");
        perrete.setEsterilizado(false);
        perrete.setnChip("810295861982509");
        db.collection("users").document(user.getEmail())
                .collection("pets")
                .document(perrete.getNombre())
                .set(perrete);*/
        return view;
    }

    private void loadHeader(View view) {
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(view)
                        .asBitmap()
                        .load(user.getPhotoUrl())
                        .into(imageViewProfile);
            } else {
                Resources resources = getContext().getResources();
                Uri uri = new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(resources.getResourcePackageName(R.mipmap.ic_default_profile_img))
                        .appendPath(resources.getResourceTypeName(R.mipmap.ic_default_profile_img))
                        .appendPath(resources.getResourceEntryName(R.mipmap.ic_default_profile_img))
                        .build();
                setUserProfileUrl(uri);
            }
            userMap = new HashMap<>();
            if (user.getDisplayName() == null || user.getDisplayName().compareTo("") == 0) {
                textViewName.setText(user.getEmail().substring(0, user.getEmail().indexOf("@")));
                userMap.put("name", textViewName.getText().toString());

            } else {
                textViewName.setText(user.getDisplayName());
                userMap.put("name", user.getDisplayName());
            }
            db.collection("users").document(user.getEmail())
                    .set(userMap);
        }
    }

    private void loadPets(View view) {
        Query query = FirebaseFirestore.getInstance()
                .collection("users").document(user.getEmail())
                .collection("pets");

        FirestoreRecyclerOptions<Perrete> options
                = new FirestoreRecyclerOptions.Builder<Perrete>()
                .setQuery(query, Perrete.class)
                .build();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PerreteAdapterMain(options);
        recyclerView.setAdapter(adapter);
    }

    private void uploadHeaderImg(Uri uri) {
        String uid = user.getUid();
        reference = FirebaseStorage.getInstance().getReference()
                .child("userProfileImg")
                .child(uid + ".jpeg");
        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al actualizar tu perfil", Toast.LENGTH_SHORT).show();
                    }
                });
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
