package com.juancoche.mydogv3.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TusMascotasAdapter extends FirestoreRecyclerAdapter<Perrete, TusMascotasAdapter.PerreteViewholder> {

    private Context context;

    public TusMascotasAdapter(@NonNull FirestoreRecyclerOptions<Perrete> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PerreteViewholder holder, int position, @NonNull Perrete model) {
        holder.name.setText(model.getNombre());
    }

    @NonNull
    @Override
    public PerreteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mis_mascotas_list, parent, false);
        context = parent.getContext();

        return new TusMascotasAdapter.PerreteViewholder(view);
    }

    class PerreteViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView name;
        TextView breed;
        ImageButton options;
        CircleImageView image;

        public PerreteViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.label_name_pet);
            breed = itemView.findViewById(R.id.label_raza);
            options = itemView.findViewById(R.id.mascotaOptions);
            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu (View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.mismascotas_list_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.eliminar:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("¿Deseas eliminar la mascota?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Eliminar mascota
                                    Toast.makeText(context, "Mascota eliminada", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
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
}
