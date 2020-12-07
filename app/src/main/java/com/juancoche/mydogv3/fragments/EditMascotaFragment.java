package com.juancoche.mydogv3.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.juancoche.mydogv3.Model.Gender;
import com.juancoche.mydogv3.Model.Perrete;
import com.juancoche.mydogv3.R;

import static android.content.ContentValues.TAG;

public class EditMascotaFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private FirebaseFirestore db;
    private EditText editTextNombre, editTextRaza, editTextPeso, editTextChip, editTextMedidas, editTextMedicacion;
    private TextView editTextFnac, editTextVacuna, editTextDesparasitacion;
    private String petName;
    private Perrete perrete;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private Switch switchEsterilizado;
    public static int REQUEST_CODE;
    private String selectedDate;
    private OnFragmentInteractionListener mListener;
    private Button aceptar, cancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_edit_mascota, container, false);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            petName = bundle.getString("nombre", ""); // Key, default value
            loadPetInfo(petName);
        }

        loadElements(view);

        return view;
    }

    private void loadElements(View view) {
        editTextFnac = view.findViewById(R.id.editTextFnac);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextRaza = view.findViewById(R.id.editTextRaza);
        editTextPeso = view.findViewById(R.id.editTextPeso);
        editTextChip = view.findViewById(R.id.editTextChip);
        editTextMedidas = view.findViewById(R.id.editTextMedidas);
        editTextVacuna = view.findViewById(R.id.editTextVacuna);
        editTextDesparasitacion = view.findViewById(R.id.editTextDesparasitacion);
        editTextMedicacion = view.findViewById(R.id.editTextMedicacion);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioMale = view.findViewById(R.id.male);
        radioFemale = view.findViewById(R.id.female);
        switchEsterilizado = view.findViewById(R.id.switchEsterilizado);
        aceptar = view.findViewById(R.id.button_aceptar);
        cancelar = view.findViewById(R.id.button_cancelar);
        editTextFnac.setOnClickListener(this);
        editTextDesparasitacion.setOnClickListener(this);
        editTextVacuna.setOnClickListener(this);
        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editTextFnac:
                REQUEST_CODE = 1;
                showDatePickerDialog();
                break;
            case R.id.editTextDesparasitacion:
                REQUEST_CODE = 2;
                showDatePickerDialog();
                break;
            case R.id.editTextVacuna:
                REQUEST_CODE = 3;
                showDatePickerDialog();
                break;
            case R.id.button_cancelar:
                getFragmentManager().popBackStack();
                break;
            case R.id.button_aceptar:
                //TODO
                if (checkFields())
                    savePetInfo();
        }
    }

    private void savePetInfo() {
        perrete = new Perrete();
        perrete.setNombre(editTextNombre.getText().toString());
        perrete.setFnac(editTextFnac.getText().toString());
        if (radioMale.isChecked())
            perrete.setGenero(Gender.MACHO.getValue());
        else if(radioMale.isChecked())
            perrete.setGenero(Gender.MACHO.getValue());
        perrete.setRaza(editTextRaza.getText().toString());
        if (switchEsterilizado.isChecked())
            perrete.setEsterilizado(true);
        else
            perrete.setEsterilizado(false);
        perrete.setPeso(editTextPeso.getText().toString());
        perrete.setnChip(editTextChip.getText().toString());
        perrete.setMedidas(editTextMedidas.getText().toString());
        perrete.setVacuna(editTextVacuna.getText().toString());
        perrete.setDesparasitacion(editTextDesparasitacion.getText().toString());
        perrete.setMedicacion(editTextMedicacion.getText().toString());

        db.collection("users").document(user.getEmail())
                .collection("pets")
                .document(perrete.getNombre())
                .set(perrete);
        getFragmentManager().popBackStack();
    }

    private boolean checkFields() {
        boolean b = true;
        if (editTextNombre.getText().toString().equals("")) {
            editTextNombre.setError("Introduce el Nombre");
            b = false;
        }
        if (editTextFnac.getText().toString().equals("")) {
            editTextFnac.setError("Introduce la fecha de nacimiento");
            b = false;
        }
        if (editTextPeso.getText().toString().equals("")) {
            editTextPeso.setError("Introduce el peso");
            b = false;
        }
        if (editTextChip.getText().toString().equals("")) {
            editTextChip.setError("Introduce el número de chip");
            b = false;
        }
        if (editTextRaza.getText().toString().equals("")) {
            editTextRaza.setError("Introduce la raza");
            b = false;
        }
        if (!radioMale.isChecked() && !radioFemale.isChecked()) {
            Toast.makeText(getContext(), "Selecciona el género", Toast.LENGTH_SHORT).show();
            b = false;
        }
        return b;
    }

    private void loadPetInfo(final String name) {

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

                                editTextNombre.setText(perrete.getNombre());
                                editTextFnac.setText(perrete.getFnac());
                                editTextRaza.setText(perrete.getRaza());
                                editTextPeso.setText(perrete.getPeso());
                                editTextChip.setText(perrete.getnChip());
                                editTextMedidas.setText(perrete.getMedidas());
                                editTextVacuna.setText(perrete.getVacuna());
                                editTextDesparasitacion.setText(perrete.getDesparasitacion());
                                editTextMedicacion.setText(perrete.getMedicacion());
//                                Log.d(TAG, "Current data: " + Gender.MACHO);
                                if (perrete.getGenero() == Gender.MACHO.getValue())
                                    radioMale.setChecked(true);
                                else if (perrete.getGenero() == Gender.HEMBRA.getValue())
                                    radioFemale.setChecked(true);
                                if (perrete.isEsterilizado())
                                    switchEsterilizado.setChecked(true);
                            }
                        } else {
                            Toast.makeText(getContext(), "No se han cargados los datos desde BD", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDatePickerDialog() {

        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        // create the datePickerFragment
        AppCompatDialogFragment newFragment = new DatePickerFragment();
        // set the targetFragment to receive the results, specifying the request code
        newFragment.setTargetFragment(EditMascotaFragment.this, REQUEST_CODE);
        // show the datePicker
        newFragment.show(fm, "datePicker");

//        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                // +1 because January is zero
//                final String selectedYear= day + "/" + (month+1) + "/" + year;
//                editText.setText(selectedYear);
//
//            }
//        });
//
//       /* DialogFragment datePicker = new DatePickerFragment();
//        datePicker.setTargetFragment(EditMascotaFragment.this, 0);
//        datePicker.show(getFragmentManager(), "date picker");*/
//
//        newFragment.show(getParentFragment().getFragmentManager(), "datePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    selectedDate = data.getStringExtra("selectedDate");
                    Log.d(TAG, "onActivityResult: " + selectedDate);
                    editTextFnac.setText(selectedDate);
                    break;
                case 2:
                    selectedDate = data.getStringExtra("selectedDate");
                    Log.d(TAG, "onActivityResult: " + selectedDate);
                    editTextDesparasitacion.setText(selectedDate);
                    break;
                case 3:
                    selectedDate = data.getStringExtra("selectedDate");
                    Log.d(TAG, "onActivityResult: " + selectedDate);
                    editTextVacuna.setText(selectedDate);
                    break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}