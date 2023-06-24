package com.example.stockcontroller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagekit.android.ImageKit;
import com.imagekit.android.ImageKitCallback;
import com.imagekit.android.entity.TransformationPosition;
import com.imagekit.android.entity.UploadError;
import com.imagekit.android.entity.UploadResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddItem extends AppCompatActivity implements ImageKitCallback, View.OnClickListener {

    private EditText etNamaBarang, etJumlah, etSatuanJumlah, etHargaSatuan;
    private Button btnSimpan;
    private ImageView imgItem;
    private DatabaseReference mDatabase;

    private AlertDialog uploadResultDialog;
    private AlertDialog loadingDialog;
    public static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_add_item);

        etNamaBarang = findViewById(R.id.et_add_namabarang);
        etJumlah = findViewById(R.id.et_add_jumlah);
        etSatuanJumlah = findViewById(R.id.et_add_satuan);
        etHargaSatuan = findViewById(R.id.et_add_harga);

        btnSimpan = findViewById(R.id.btn_add_save);
        imgItem = findViewById(R.id.image_additem_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ImageKit.Companion.init(
                getApplicationContext(),
                "public_DI0wRDL93Khpd5nmr1qJsL3Zyp4=",
                "https://ik.imagekit.io/stockcontrollerpam",
                TransformationPosition.PATH,
                "http://www.yourserver.com/auth"
        );

        btnSimpan.setOnClickListener(this);
        imgItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSimpan) {
            if (TextUtils.isEmpty(etNamaBarang.getText().toString())) {
                Toast.makeText(this, "Nama barang tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                insertItemFirebase();
                uploadImage();
            }
        }

        if (v == imgItem) {
            selectImage();
        }
    }

    public void insertItemFirebase() {
        ModelBarang item = new ModelBarang(
                etNamaBarang.getText().toString(),
                etJumlah.getText().toString(),
                etSatuanJumlah.getText().toString(),
                etHargaSatuan.getText().toString()
        );

        mDatabase.child("item").push().setValue(item);
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

        String filename = etNamaBarang.getText().toString().trim() + ".jpg"; // habis ini diubah jadi id firebase
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                if (data == null) return;

                InputStream imageStream = getContentResolver().openInputStream(data.getData());
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap = selectedImage;
                imgItem.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Mohon maaf, terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sepertinya anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

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