package com.example.stockcontroller;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btnMasuk;
    private TextView btnDaftar, btnForgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);

        btnMasuk  = (Button)  findViewById(R.id.btn_login);
        btnDaftar = findViewById(R.id.tv_login_signin);
        btnForgotPassword = findViewById(R.id.tv_login_forgot_password);

        btnMasuk.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (v.getId() == R.id.btn_login) {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Enter your Email or Password", Toast.LENGTH_SHORT).show();
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}