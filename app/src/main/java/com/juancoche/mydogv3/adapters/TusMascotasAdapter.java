package com.juancoche.mydogv3.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.juancoche.mydogv3.Model.Gender;
import com.juancoche.mydogv3.Model.Perrete;
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
        if (model.getGenero() == Gender.MACHO.getValue())
            holder.gender.setText("Género: Macho");
        else if (model.getGenero() == Gender.HEMBRA.getValue())
            holder.gender.setText("Género: Hembra");
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

    class PerreteViewholder extends RecyclerView.ViewHolder /*implements View.OnClickListener, PopupMenu.OnMenuItemClickListener*/ {

        TextView name;
        TextView breed;
        TextView gender;
        TextView fnac;
        CircleImageView image;

        public PerreteViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.label_name_pet);
            breed = itemView.findViewById(R.id.label_raza);
            gender = itemView.findViewById(R.id.label_genero);
            fnac = itemView.findViewById(R.id.label_fNac);
        }
    }
}
