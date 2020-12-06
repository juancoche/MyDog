package com.juancoche.mydogv3.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TusMascotasAdapter extends FirestoreRecyclerAdapter<Perrete, TusMascotasAdapter.PerreteViewholder> implements View.OnClickListener {

    private Context context;
    private View.OnClickListener listener;
    private View view;

    public TusMascotasAdapter(@NonNull FirestoreRecyclerOptions<Perrete> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PerreteViewholder holder, int position, @NonNull Perrete model) {
        holder.name.setText(model.getNombre());
        if(model.getUrlImg() != null) {
            Glide.with(view)
                    .asBitmap()
                    .load(Uri.parse(model.getUrlImg()))
                    .into(holder.image);
        }
        holder.fnac.setText("F. Nac: " + model.getFnac());
        holder.breed.setText("Raza: " + model.getRaza());
    }

    @NonNull
    @Override
    public PerreteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mis_mascotas_list, parent, false);
        context = parent.getContext();

        view.setOnClickListener(this);

        return new TusMascotasAdapter.PerreteViewholder(view);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    class PerreteViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView name;
        TextView breed;
        TextView gender;
        TextView fnac;
        ImageButton options;
        CircleImageView image;

        public PerreteViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.label_name_pet);
            breed = itemView.findViewById(R.id.label_raza);
            gender = itemView.findViewById(R.id.label_genero);
            fnac = itemView.findViewById(R.id.label_fNac);
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
