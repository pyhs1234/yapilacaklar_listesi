package com.example.proje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProje extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproje);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("proje");
        TextView tw1 = (TextView)findViewById(R.id.textView15);
        TextView tw2 = (TextView)findViewById(R.id.textView17);
        TextView tw3 =(TextView)findViewById(R.id.textView2);
        final EditText et= (EditText)findViewById(R.id.editText8);
        final EditText et2 = (EditText)findViewById(R.id.editText9);
        final EditText et3 = (EditText)findViewById(R.id.editText);
        Button ekle = (Button)findViewById(R.id.projeekle);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et3.getText().toString();
                myRef.child(id).child("projeadı").setValue(et.getText().toString());
                myRef.child(id).child("projeaciklama").setValue(et2.getText().toString());
                Toast.makeText(AddProje.this, "Proje Eklendi", Toast.LENGTH_LONG).show();
            }
        });
    }
}
