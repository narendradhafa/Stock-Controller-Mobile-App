package com.example.stockcontroller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.imagekit.android.ImageKit;
import com.imagekit.android.entity.TransformationPosition;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditItem extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageItem;
    private Button btnSimpan, btnHapus;

    public static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_edit_item);

        imageItem = findViewById(R.id.image_edititem_item);

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
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
}