package com.example.proje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminGiris extends Activity {
    private Spinner sp1;
    private Spinner sp2;
    private DatabaseReference projeref;
    private DatabaseReference kullref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admingiris);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        projeref = database.getReference("proje");
        kullref = database.getReference("kullanıcı");
        final ArrayList<String> projekey = new ArrayList<String>();
        final TextView tw2 = (TextView)findViewById(R.id.textView11);
        final TextView tw3 = (TextView)findViewById(R.id.textView12);
        sp1 = (Spinner)findViewById(R.id.spinner);
        sp2 = (Spinner)findViewById(R.id.spinner2);
        Button proje = (Button)findViewById(R.id.proje);
        Button ekle = (Button)findViewById(R.id.projeekle);
        projeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> projeList = new ArrayList<String>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String a = areaSnapshot.getKey();
                    String b = areaSnapshot.child("projeadı").getValue(String.class);
                    projekey.add(a);
                    projeList.add(b);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminGiris.this, android.R.layout.simple_spinner_item, projeList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hata", "Okuma hatası", databaseError.toException());
            }
        });
        kullref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> kullaniciadi = new ArrayList<String>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String b = areaSnapshot.child("ad").getValue(String.class);
                    kullaniciadi.add(b);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AdminGiris.this, android.R.layout.simple_spinner_item, kullaniciadi);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hata", "Okuma hatası", databaseError.toException());
            }
        });
        proje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String a = sp1.getSelectedItem().toString();
                projeref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String projeID ="";
                        for(DataSnapshot areaSnapshot : dataSnapshot.getChildren()){
                            if(a == areaSnapshot.child("projeadı").getValue(String.class)){
                                projeID = areaSnapshot.getKey();
                            }
                        }
                        Intent proje = new Intent(AdminGiris.this, Proje.class);
                        proje.putExtra("ProjeKey",projeID);
                        startActivity(proje);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Intent proje = new Intent(AdminGiris.this, Proje.class);
                //proje.putExtra("ProjeKey",projeID);
                //startActivity(proje);
            }
        });
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addproje = new Intent(AdminGiris.this, AddProje.class);
                startActivity(addproje);
            }
        });
    }
}
