package com.juancoche.mydogv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.bumptech.glide.Glide;

public class RecyclerViewAdapterMainUltimasFotos extends RecyclerView.Adapter<RecyclerViewAdapterMainUltimasFotos.ViewHolder> {

    private ArrayList<Integer> fotos = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterMainUltimasFotos(ArrayList<Integer> fotos, Context mContext) {
        this.fotos = fotos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lastphotos, parent, false);
        return new RecyclerViewAdapterMainUltimasFotos.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(fotos.get(position))
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.foto_ultimas);
        }
    }
}
