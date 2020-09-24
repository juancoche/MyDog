package com.juancoche.mydogv3.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.juancoche.mydogv3.R;

import static com.juancoche.mydogv3.activities.login.SignUpActivity.isValidEmail;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button goToLogin;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        goToLogin = findViewById(R.id.button_returnToLogin);
        editTextEmail = findViewById(R.id.editTextEmailReset);
        buttonResetPassword = findViewById(R.id.button_reset_password);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (isValidEmail(editTextEmail.getText().toString())) {
                    editTextEmail.setError(null, null);
                } else {
                    editTextEmail.setError("El email no es correcto");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isValidEmail(editTextEmail.getText().toString())) {
                    mAuth.sendPasswordResetEmail(editTextEmail.getText().toString())
                            .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() { // Se realiza la acción una vez completada la tarea
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ForgotPasswordActivity.this, "Se ha enviado un email para resetear tu contraseña",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Comprueba que el email introducido sea correcto",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}