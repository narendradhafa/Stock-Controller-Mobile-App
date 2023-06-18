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

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText etNama, etEmail, etTelepon, etPassword;
    private Button btnDaftar;
    private TextView btnMasuk;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_register);

        etNama = (EditText) findViewById(R.id.et_signin_name);
        etEmail = (EditText) findViewById(R.id.et_signin_email);
        etTelepon = (EditText) findViewById(R.id.et_signin_notelp);
        etPassword = (EditText) findViewById(R.id.et_signin_password);

        btnDaftar = (Button) findViewById(R.id.btnSignIn);
        btnMasuk  = (TextView) findViewById(R.id.tv_signin_login);

        mAuth = FirebaseAuth.getInstance();

        btnDaftar.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                signUp(etEmail.getText().toString(), etPassword.getText().toString());
                break;
        }
    }
    public void signUp(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed - in user 's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);//*
                            Toast.makeText(Register.this, user.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
// If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            //* updateUI(null);//
                        }
                    }
                });
    }

    public void login(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new
                        OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
// Sign in success, update UI with the signed - in user 's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Register.this, user.toString(), Toast.LENGTH_SHORT).show();

                                } else {
// If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
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


