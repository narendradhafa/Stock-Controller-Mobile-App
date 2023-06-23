package com.example.stockcontroller;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText etNama, etEmail, etTelepon, etPassword;
    Button btnDaftar;
    TextView btnMasuk;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        etNama = (EditText) findViewById(R.id.et_signin_name);
        etEmail = (EditText) findViewById(R.id.et_signin_email);
        etTelepon = (EditText) findViewById(R.id.et_signin_notelp);
        etPassword = (EditText) findViewById(R.id.et_signin_password);

        btnDaftar = (Button) findViewById(R.id.btnSignUp);
        btnMasuk = (TextView) findViewById(R.id.tv_signin_login);


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
//                signUp(email, password);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user!=null) {
                                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(etNama.getText().toString())
                                                .build();
                                        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                                                    String userId = user.getUid();

                                                    User userr = new User();
                                                    userr.setNama(etNama.getText().toString());
                                                    userr.setEmail(etEmail.getText().toString());
                                                    userr.setPhone(etTelepon.getText().toString());

                                                    userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                reload();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Gagal menyimpan data pengguna ke database", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Gagal memperbarui profil pengguna", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                    Toast.makeText(Register.this, user.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            result = false;
        } else {
            etEmail.setError(null);
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            result = false;
        } else {
            etPassword.setError(null);
        }
        return result;
    }
    
}