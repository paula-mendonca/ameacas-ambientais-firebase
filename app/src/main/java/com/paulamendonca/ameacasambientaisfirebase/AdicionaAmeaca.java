package com.paulamendonca.ameacasambientaisfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AdicionaAmeaca extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    DatabaseReference Ameacas = root.child(MainActivity.AMEACAS_KEY);
    EditText txtEnd, txtData, txtDesc;
    public static final int CAMERA_CALL = 1022;
    Bitmap bmp;
    ImageView image;
    Boolean hasImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_ameaca);

        txtEnd = findViewById(R.id.txtEnd);
        txtData = findViewById(R.id.txtData);
        txtDesc = findViewById(R.id.txtDesc);
        image = findViewById(R.id.image);
    }

    public String loadImage () {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
        return Base64.encodeToString(byteOut.toByteArray(), Base64.DEFAULT);
    }

    public void addAmeaca (View v){
        Ameaca a = new Ameaca();
        a.setEnd(txtEnd.getText().toString());
        a.setData(txtData.getText().toString());
        a.setDesc(txtDesc.getText().toString());

        if (hasImage) {
            String bmpEncoded = loadImage();
            hasImage = false;
            a.setImage(bmpEncoded);
        }

        String key = Ameacas.push().getKey();
        Ameacas.child(key).setValue(a);
        finish();
    }

    public void takePicture (View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_CALL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CALL && resultCode == RESULT_OK) {
            bmp = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bmp);
            hasImage = true;
        }
    }
}