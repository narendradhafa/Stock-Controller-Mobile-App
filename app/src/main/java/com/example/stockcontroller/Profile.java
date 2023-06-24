package com.example.stockcontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView tvNama, tvPhone, tvEmail;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_profile);

        tvNama = findViewById(R.id.tv_profile_namauser);
        tvPhone = findViewById(R.id.tv_profile_telpon);
        tvEmail = findViewById(R.id.tv_profile_email);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase
                .getInstance("https://stock-controller-64fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String email = user.getEmail();
            getUserData(userId, email);
        } else {
            Toast.makeText(
                    getBaseContext(),
                    "Gagal mendapatkann data user",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void getUserData(String userId, String email) {
        try {
            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        populateUserData(user, email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("ERROR", "onCancelled: " + error);
                    Toast.makeText(
                            getBaseContext(),
                            "Gagal Mendapatkan Data Profile, Silahkan periksa koneksi Internet Anda",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        } catch (Exception e) {
            Log.e("ERROR", "onCancelled: " + e);
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void populateUserData(User user, String email) {
        tvNama.setText(user.getNama());
        tvEmail.setText(email);
        tvPhone.setText(user.getPhone());
    }
}