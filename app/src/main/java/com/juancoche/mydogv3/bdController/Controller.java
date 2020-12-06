package com.juancoche.mydogv3.bdController;

import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.juancoche.mydogv3.Perrete;

public class Controller {

    private FirebaseFirestore db;
    private FirebaseUser user;

    public void main(String[] args) {

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private Perrete loadPetInfo(final String name) {

        final Perrete[] perrete = new Perrete[1];

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

                                perrete[0] = new Perrete(document.getData());
                                /*if (perrete.getUrlImg() != null) {
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
                                    label_esterilizado.setText("Esterilizado: No");*/
                            }
                        } else {
//                            Toast.makeText(getContext(), "No se han cargados los datos desde BD", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return perrete[0];
    }
}
