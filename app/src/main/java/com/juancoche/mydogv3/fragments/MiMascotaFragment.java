package com.juancoche.mydogv3.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;
import com.juancoche.mydogv3.activities.login.LoginActivity;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import static android.content.ContentValues.TAG;

public class MiMascotaFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private FirebaseUser user;
    private FirebaseFirestore db;
    private TextView label_name_pet;
    private TextView label_chip;
    private ImageView pet_image;
    private StorageReference reference;
    private String petName;
    private Perrete perrete;
    private TextView label_fNac;
    private TextView label_genero;
    private TextView label_raza;
    private TextView label_esterilizado;
    private TextView label_peso;
    private ImageButton mascotaOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_mi_mascota, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        label_name_pet = view.findViewById(R.id.label_name_pet);
        label_chip = view.findViewById(R.id.label_chip);
        pet_image = view.findViewById(R.id.pet_image);
        label_fNac = view.findViewById(R.id.label_fNac);
        label_genero = view.findViewById(R.id.label_genero);
        label_raza = view.findViewById(R.id.label_raza);
        label_esterilizado = view.findViewById(R.id.label_castrado);
        label_peso = view.findViewById(R.id.label_peso);
        mascotaOptions = view.findViewById(R.id.mascotaOptions);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            petName = bundle.getString("nombre", ""); // Key, default value
            loadPetInfo(view, petName);
        }
        petListener();
        petImgSetOnClickListener();
        mascotaOptions.setOnClickListener(this);




        return view;
    }

    private void petImgSetOnClickListener() {
        pet_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                pet_image.setImageURI(r.getUri());
                                uploadPetImg(r.getUri());
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
    }

    private void petListener() {
        final DocumentReference docRef = db.collection("users").document(user.getEmail())
                .collection("pets")
                .document(petName);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    perrete = new Perrete(snapshot.getData());
                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void loadPetInfo(final View view, final String name) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference petsRef = db.collection("users")
                .document(user.getEmail())
                .collection("pets");

        petsRef.whereEqualTo("nombre", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                perrete = new Perrete(document.getData());
                                if (perrete.getUrlImg() != null) {
                                    Glide.with(view)
                                            .asBitmap()
                                            .load(Uri.parse(perrete.getUrlImg()))
                                            .into(pet_image);
                                }
                                label_name_pet.setText(perrete.getNombre());
                                label_chip.setText("Chip: " + perrete.getnChip());
                                label_fNac.setText("F. Nac: " + perrete.getFnac());
                                label_genero.setText("Género: " + perrete.getGenero());
                                label_peso.setText("Peso: " + perrete.getPeso());
                                label_raza.setText("Raza: " + perrete.getRaza());
                                if (perrete.isEsterilizado())
                                    label_esterilizado.setText("Esterilizado: Si");
                                else
                                    label_esterilizado.setText("Esterilizado: No");
                            }
                        } else {
                            Toast.makeText(getContext(), "No se han cargados los datos desde BD", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadPetImg(Uri uri) {
        reference = FirebaseStorage.getInstance().getReference()
                .child("petProfileImg")
                .child(petName + "Profile.jpeg");
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
                        DocumentReference dr = db.collection("users").document(user.getEmail())
                                .collection("pets")
                                .document(petName);
                        Log.d(TAG, "Current data: " + uri);
                        dr.update("urlImg", uri.toString());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        showPopupMenu(v);
    }

    private void showPopupMenu (View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.mimascota_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eliminar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setMessage("¿Deseas eliminar la mascota?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.collection("users").document(user.getEmail())
                                        .collection("pets")
                                        .document(petName)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                reloadFragment();
                                                getFragmentManager().popBackStack();
                                                Toast.makeText(getContext(), "Has eliminado la mascota", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
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
            case R.id.editar:
                goToPetProfile(new EditMascotaFragment(), petName);
                return true;
            default:
                return false;
        }
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
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
}