package com.example.stockcontroller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagekit.android.ImageKit;
import com.imagekit.android.ImageKitCallback;
import com.imagekit.android.entity.TransformationPosition;
import com.imagekit.android.entity.UploadError;
import com.imagekit.android.entity.UploadResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditItemActivity extends AppCompatActivity implements ImageKitCallback, View.OnClickListener {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ImageView imageItem;
    private Button btnSimpan, btnHapus;
    EditText etNameBarang, etJumlah, etSatuan, etHargaSatuan;

    private AlertDialog uploadResultDialog;
    private AlertDialog loadingDialog;
    public static final int PICK_IMAGE = 1;
    private Bitmap bitmap;
    private String userId, idBarang, namaBarang, jumlah, satuan, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_edit_item);

        idBarang = getIntent().getStringExtra("extra_id");
        namaBarang = getIntent().getStringExtra("extra_nama");
        jumlah = getIntent().getStringExtra("extra_jumlah");
        satuan = getIntent().getStringExtra("extra_satuan");
        harga = getIntent().getStringExtra("extra_harga");

        imageItem = findViewById(R.id.image_edititem_item);
        btnSimpan = findViewById(R.id.btn_edit_save);
        btnHapus = findViewById(R.id.btn_edit_delete);

        etNameBarang = (EditText) findViewById(R.id.et_add_namabarang);
        etJumlah = (EditText) findViewById(R.id.et_add_jumlah);
        etSatuan = (EditText) findViewById(R.id.et_add_satuan);
        etHargaSatuan = (EditText) findViewById(R.id.et_add_harga);

        initData();

        imageItem.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase
                .getInstance("https://stock-controller-64fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        userId = firebaseAuth.getUid();

        ImageKit.Companion.init(
                getApplicationContext(),
                "public_DI0wRDL93Khpd5nmr1qJsL3Zyp4=",
                "https://ik.imagekit.io/stockcontrollerpam",
                TransformationPosition.PATH,
                "http://www.yourserver.com/auth"
        );

        initListener();
    }

    private void initData() {
        etNameBarang.setText(namaBarang);
        etJumlah.setText(jumlah);
        etSatuan.setText(satuan);
        etHargaSatuan.setText(harga);
    }

    private void initListener() {
        btnSimpan.setOnClickListener(view -> {
            if (validateForm()) {
                updateProfile();
            }
        });

        btnHapus.setOnClickListener(view -> {
            if (validateForm()) {
                deleteData();
            }
        });
    }

    private void updateProfile() {
        ModelBarang newData = populateData();

        if (userId != null) {
            databaseReference.child("item").child(userId).child(idBarang).setValue(newData)
                    .addOnSuccessListener(v -> {
                        Toast.makeText(getApplicationContext(), "Barang berhasil di Update", Toast.LENGTH_LONG).show();
                        backToDashboard();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Error upload", e.toString());
                        Toast.makeText(getApplicationContext(), "Gagal meng-update barang", Toast.LENGTH_LONG).show();
                    });
        }
    }

    private void deleteData() {
        if (userId != null) {
            DatabaseReference reference = databaseReference.child("item").child(userId);
            reference.removeValue();
        }
    }

    private void backToDashboard() {
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etNameBarang.getText().toString())) {
            etNameBarang.setError("Required");
            result = false;
        } else {
            etNameBarang.setError(null);
        }
        if (TextUtils.isEmpty(etJumlah.getText().toString())) {
            etJumlah.setError("Required");
            result = false;
        } else {
            etJumlah.setError(null);
        }
        if (TextUtils.isEmpty(etSatuan.getText().toString())) {
            etSatuan.setError("Required");
            result = false;
        } else {
            etSatuan.setError(null);
        }
        if (TextUtils.isEmpty(etHargaSatuan.getText().toString())) {
            etHargaSatuan.setError("Required");
            result = false;
        } else {
            etHargaSatuan.setError(null);
        }
        return result;
    }

    private ModelBarang populateData() {
        String nama = etNameBarang.getText().toString();
        String jumlah = etJumlah.getText().toString();
        String satuan = etSatuan.getText().toString();
        String harga = etHargaSatuan.getText().toString();

        return new ModelBarang(
                idBarang, nama, jumlah, satuan, harga
        );
    }

    @Override
    public void onClick(View v) {
        if (v == imageItem) {
            selectImage();
        }

        if (v == btnSimpan) {
            uploadImage();

            // lanjutin kodingan firebase buat ngesavenya

        }

        if (v == btnHapus) {
            // hapus data ini di firebase
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                if (data == null) return;

                InputStream imageStream = getContentResolver().openInputStream(data.getData());
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap = selectedImage;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Mohon maaf, terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sepertinya anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void uploadImage() {
        if (bitmap == null) return;

        loadingDialog = new AlertDialog.Builder(this).setMessage("Gambar sedang diupload...").setCancelable(false).show();

        String filename = "tes.jpg"; // habis ini diubah jadi id firebase
        ImageKit.Companion.getInstance().uploader().upload(
                bitmap,
                filename,
                false,
                null,
                "images/item",
                false,
                null,
                "tags",
                this
        );
    }

    @Override
    public void onError(@NonNull UploadError uploadError) {
        loadingDialog.dismiss();
        uploadResultDialog = new AlertDialog.Builder(this)
                .setTitle("Upload Failed")
                .setMessage("Error: " + uploadError.getMessage())
                .setNeutralButton("Ok", null)
                .show();
    }

    @Override
    public void onSuccess(@NonNull UploadResponse uploadResponse) {
        loadingDialog.dismiss();
        uploadResultDialog = new AlertDialog.Builder(this)
                .setTitle("Upload Complete")
                .setMessage("The uploaded image can be accessed using URL: " + uploadResponse.getUrl())
                .setNeutralButton("Ok", null)
                .show();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (uploadResultDialog != null) {
            uploadResultDialog.dismiss();
        }
    }
}