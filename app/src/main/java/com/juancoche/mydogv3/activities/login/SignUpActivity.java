package com.juancoche.mydogv3.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juancoche.mydogv3.R;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button buttonReturnToLogin;
    private Button buttonRegisterNewAccount;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        buttonReturnToLogin = findViewById(R.id.button_return);
        buttonRegisterNewAccount = findViewById(R.id.button_signUp);
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)
                        // Flags para que al pulsar el botón atrás desde el login, no volvamos a esta activity
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        buttonRegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String repPassword = editTextRepeatPassword.getText().toString();
                if (isValidEmail(email) && isValidPassword(password) && isValidRepeatPassword(password, repPassword)) {
                    createAccount(email, password);
                } else {
                    Toast.makeText(SignUpActivity.this, "Error, comprueba que los campos sean correctos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Validación en tiempo real de los campos introducidos
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

        editTextRepeatPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (isValidRepeatPassword(editTextPassword.getText().toString(), editTextRepeatPassword.getText().toString())) {
                    editTextRepeatPassword.setError(null, null);
                } else {
                    editTextRepeatPassword.setError("Las contraseñas deben coincidir");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  //Enviar email de verificación
                            Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification()
                                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() { // Se realiza la acción una vez completada la tarea
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUpActivity.this, "Se ha enviado un email de confirmación a tu cuenta de correo",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            });

                        } else {
                            Log.w(">>>>>>LOGIN FAILED<<<<<<<", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Ha ocurrido un error, inténtalo de nuevo",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Reglas de validación de los campos
    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        // Contraseña: Al menos 1 numero / 1 minusc / 1 mayusc / 1 caracter / sin espacios / min 8
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-_])(?=\\S+$).{8,}$";
        Pattern passwordPattern = Pattern.compile(regexPattern);
        return passwordPattern.matcher(password).matches();
    }

    private boolean isValidRepeatPassword(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}