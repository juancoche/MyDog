package com.juancoche.mydogv3.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.R;

import static com.juancoche.mydogv3.activities.login.SignUpActivity.isValidEmail;
import static com.juancoche.mydogv3.activities.login.SignUpActivity.isValidPassword;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button buttonNewAccount, buttonLoginGoogle, buttonLoginEmail;
    private EditText editTextEmail, editTextPassword;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLoginEmail = findViewById(R.id.button_login_email);
        forgotPassword = findViewById(R.id.textViewForgotPassword);
        buttonLoginGoogle = findViewById(R.id.button_login_google);
        buttonNewAccount = findViewById(R.id.button_register);

        buttonLoginEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (isValidEmail(email) && isValidPassword(password)) {
                    loginWithEmail(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Error, comprueba que los campos sean correctos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

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

        editTextPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (isValidPassword(editTextPassword.getText().toString())) {
                    editTextPassword.setError(null, null);
                } else {
                    editTextPassword.setError("La contraseña debe contener 8 caracteres, 1 num, 1 minus, 1 mayus, 1 caracter especial");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void loginWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "Usuario logado",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Primer debes confirmar tu email",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(">>>>>>LOGIN SUCCESS<<<<<<<", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(">>>>>>LOGIN FAILED<<<<<<<", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}