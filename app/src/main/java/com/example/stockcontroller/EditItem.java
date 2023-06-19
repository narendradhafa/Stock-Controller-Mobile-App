package com.example.stockcontroller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.imagekit.android.ImageKit;
import com.imagekit.android.ImageKitCallback;
import com.imagekit.android.entity.TransformationPosition;
import com.imagekit.android.entity.UploadError;
import com.imagekit.android.entity.UploadResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditItem extends AppCompatActivity implements ImageKitCallback, View.OnClickListener {

    private ImageView imageItem;
    private Button btnSimpan, btnHapus;

    private AlertDialog uploadResultDialog;
    private AlertDialog loadingDialog;
    public static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_edit_item);

        imageItem = findViewById(R.id.image_edititem_item);
        btnSimpan = findViewById(R.id.btn_edit_save);
        btnHapus  = findViewById(R.id.btn_edit_delete);

        imageItem.setOnClickListener(this);

        ImageKit.Companion.init(
                getApplicationContext(),
                "public_DI0wRDL93Khpd5nmr1qJsL3Zyp4=",
                "https://ik.imagekit.io/stockcontrollerpam",
                TransformationPosition.PATH,
                "http://www.yourserver.com/auth"
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