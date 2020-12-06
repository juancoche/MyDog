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

public class PerreteAdapterMain extends FirestoreRecyclerAdapter<Perrete, PerreteAdapterMain.PerreteViewholder> {


    public PerreteAdapterMain(@NonNull FirestoreRecyclerOptions<Perrete> options) {
        super(options);
    }

    @NonNull
    @Override
    public PerreteViewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);

        return new PerreteViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull PerreteViewholder perreteViewholder, int i, @NonNull Perrete perrete) {
        perreteViewholder.name.setText(perrete.getNombre());
    }

    class PerreteViewholder extends RecyclerView.ViewHolder {

        TextView name;


        public PerreteViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.label_name_pet);

        }
    }
}
