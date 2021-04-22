package com.paulamendonca.ameacasambientaisfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static final String AMEACAS_KEY = "ameacas";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    DatabaseReference Ameacas = root.child(AMEACAS_KEY);
    FirebaseListAdapter<Ameaca> listAdapter;
    ListView listAmeaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAmeaca = findViewById(R.id.listAmeaca);
        listAdapter = new FirebaseListAdapter<Ameaca>(this, Ameaca.class, R.layout.ameaca_item, Ameacas) {
            @Override
            protected void populateView(View v, Ameaca model, int position) {
                TextView txtAmeacaEnd = v.findViewById(R.id.txtAmeacaEnd);
                TextView txtAmeacaData = v.findViewById(R.id.txtAmeacaData);
                ImageView imageItem = v.findViewById(R.id.imageItem);

                txtAmeacaEnd.setText(model.getEnd());
                txtAmeacaData.setText(model.getData());
                if (model.getImage() != null) {
                    byte imageData[] = Base64.decode(model.getImage(), Base64.DEFAULT);
                    Bitmap img = BitmapFactory.decodeByteArray(imageData,0, imageData.length);
                    imageItem.setImageBitmap(img);
                }

            }
        };
        listAmeaca.setAdapter(listAdapter);
        listAmeaca.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    DatabaseReference item = listAdapter.getRef(position);
                    item.removeValue();
                return false;
            }
        });

        listAmeaca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference item = listAdapter.getRef(position);
                changeToUpdate(item.getKey(), listAdapter.getItem(position));
            }
        });
    }

    public void changeToAdd (View v) {
        Intent it = new Intent(getBaseContext(), AdicionaAmeaca.class);
        startActivity(it);
    }

    public void changeToUpdate (String key, Ameaca a) {
        Intent it = new Intent(getBaseContext(), EditaAmeaca.class);
        it.putExtra("KEY", key);
        it.putExtra("AME", a);
        startActivity(it);
    }
}