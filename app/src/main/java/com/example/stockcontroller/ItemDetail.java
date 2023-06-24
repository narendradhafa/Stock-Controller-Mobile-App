package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.imagekit.android.ImageKit;
import com.imagekit.android.entity.TransformationPosition;
import com.squareup.picasso.Picasso;

public class ItemDetail extends AppCompatActivity implements View.OnClickListener {

    Button btnHapus;
    ImageView btnEdit, imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_item_detail);

        btnEdit = findViewById(R.id.btn_detail_edititem);
        btnHapus = findViewById(R.id.btn_detail_delete);
        imageItem = findViewById(R.id.image_itemdetail_item);

        btnEdit.setOnClickListener(this);

        ImageKit.Companion.init(
                getApplicationContext(),
                "public_DI0wRDL93Khpd5nmr1qJsL3Zyp4=",
                "https://ik.imagekit.io/stockcontrollerpam",
                TransformationPosition.PATH,
                "http://www.yourserver.com/auth"
        );

        Picasso.get().load(ImageKit.Companion.getInstance()
                .url(
                        findViewById(R.id.tv_detail_itemname).toString().trim() + ".jpg",
                        TransformationPosition.QUERY
                )
                .aspectRatio(16, 9)
                .create()).into(imageItem);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ItemDetail.this, EditItem.class);
        startActivity(intent);
    }
}