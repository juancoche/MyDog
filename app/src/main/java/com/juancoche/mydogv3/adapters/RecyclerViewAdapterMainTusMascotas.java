package com.juancoche.mydogv3.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.juancoche.mydogv3.Perrete;
import com.juancoche.mydogv3.R;

public class RecyclerViewAdapterMainTusMascotas extends RecyclerView.Adapter<RecyclerViewAdapterMainTusMascotas.ViewHolder> {

    private ArrayList<Perrete> perretes = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterMainTusMascotas(ArrayList<Perrete> perretes, Context mContext) {
        this.perretes = perretes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(perretes.get(position).getImagen())
                .into(holder.image);
        holder.name.setText(perretes.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return perretes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.label_name_pet);
        }
    }
}
