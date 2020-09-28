package com.juancoche.mydogv3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;
        TextView breed;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.label_name_pet);
            breed = itemView.findViewById(R.id.label_raza);
        }
    }
}

