package com.juancoche.mydogv3.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterTusMascotas extends RecyclerView.Adapter<RecyclerViewAdapterTusMascotas.ViewHolder> {

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterTusMascotas(ArrayList<Perrete> perretes, Context mContext) {
        this.perretes = perretes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterTusMascotas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mis_mascotas_list, parent, false);
        return new RecyclerViewAdapterTusMascotas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTusMascotas.ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(perretes.get(position).getImagen())
                .into(holder.image);
        holder.name.setText(perretes.get(position).getNombre());
        holder.breed.setText(perretes.get(position).getRaza());

    }

    @Override
    public int getItemCount() {
        return perretes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        CircleImageView image;
        TextView name;
        TextView breed;
        ImageButton options;


        public ViewHolder(@NonNull View itemView) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("¿Deseas eliminar la mascota?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Eliminar mascota
                                    Toast.makeText(mContext, "Mascota eliminada", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(mContext, "Cancelado", Toast.LENGTH_LONG).show();
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


