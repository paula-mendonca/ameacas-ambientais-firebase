package com.paulamendonca.ameacasambientaisfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.paulamendonca.ameacasambientaisfirebase.Ameaca;

public class EditaAmeaca extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    DatabaseReference ameacas = root.child(MainActivity.AMEACAS_KEY);
    EditText txtEnd, txtData, txtDesc;
    Ameaca current;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_ameaca);

        txtEnd = findViewById(R.id.txtEnd);
        txtData = findViewById(R.id.txtData);
        txtDesc = findViewById(R.id.txtDesc);

        key = getIntent().getStringExtra("KEY");

        current = (Ameaca) getIntent().getSerializableExtra("AME");

        txtEnd.setText(current.getEnd());
        txtData.setText(current.getData());
        txtDesc.setText(current.getDesc());
    }

    public void updateAmeaca (View v) {
        current.setEnd(txtEnd.getText().toString());
        current.setData(txtData.getText().toString());
        current.setDesc(txtDesc.getText().toString());

        ameacas.child(key).setValue(current);

        finish();
    }
}