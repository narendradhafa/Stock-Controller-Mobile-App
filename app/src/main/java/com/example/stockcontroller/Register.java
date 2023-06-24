package com.example.stockcontroller;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText etNama, etEmail, etTelepon, etPassword;
    Button btnDaftar;
    TextView btnMasuk;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase
                .getInstance("https://stock-controller-64fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        etNama = (EditText) findViewById(R.id.et_signin_name);
        etEmail = (EditText) findViewById(R.id.et_signin_email);
        etTelepon = (EditText) findViewById(R.id.et_signin_notelp);
        etPassword = (EditText) findViewById(R.id.et_signin_password);

        btnDaftar = (Button) findViewById(R.id.btnSignUp);
        btnMasuk = (TextView) findViewById(R.id.tv_signin_login);

        btnMasuk.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        btnDaftar.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                uploadDataToDatabase(user.getUid());
                            } else {
                                Toast.makeText(Register.this, "Register First", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void uploadDataToDatabase(String id) {
        String name = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etTelepon.getText().toString();

        User newUser = new User(
                name,
                email,
                phone
        );

        databaseReference.child("users").child(id).setValue(newUser)
                .addOnSuccessListener(v -> {
                    Toast.makeText(getApplicationContext(), "Akun berhasil didaftarkan", Toast.LENGTH_LONG).show();
                    navigateToLogin();
                })
                .addOnFailureListener(e -> {
                    Log.e("Error upload", e.toString());
                    Toast.makeText(getApplicationContext(), "Gagal menyimpan data pengguna", Toast.LENGTH_LONG).show();
                });
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

    private void navigateToLogin(){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(Register.this, "Register First",
                    Toast.LENGTH_SHORT).show();
        }
    }
}