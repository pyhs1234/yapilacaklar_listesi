package com.example.proje;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Proje extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proje);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String projekey = getIntent().getExtras().getString("ProjeKey");
        final DatabaseReference projeref = database.getReference("proje");
        TextView tw1 = (TextView)findViewById(R.id.textView14);
        TextView tw2 = (TextView)findViewById(R.id.textView21);
        TextView tw4 = (TextView)findViewById(R.id.textView16);
        TextView tw6 = (TextView)findViewById(R.id.textView24);
        TextView tw3 = (TextView)findViewById(R.id.textView18);
        final EditText et1 = (EditText)findViewById(R.id.editText11);
        final EditText et2 = (EditText)findViewById(R.id.editText12);
        final EditText et4 = (EditText)findViewById(R.id.editText14);
        final EditText et6 = (EditText)findViewById(R.id.editText6);
        final Spinner sp1 = (Spinner)findViewById(R.id.spinner3);
        Button addMission = (Button)findViewById(R.id.button3);
        final Button deleteMission = (Button)findViewById(R.id.sil);
        Button saveMission = (Button)findViewById(R.id.kaydet);
        Button goster = (Button)findViewById(R.id.goster);
        final CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        projeref.child(projekey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a="";
                a = dataSnapshot.child("projeadı").getValue(String.class);
                et1.setText(a);
                String b = "";
                b = dataSnapshot.child("projeaciklama").getValue(String.class);
                et2.setText(b);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hata", "Okuma hatası", databaseError.toException());
            }
        });
        projeref.child(projekey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> gorevList = new ArrayList<String>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                    if(areaSnapshot.child("Görev").exists()){
                        gorevList.add(areaSnapshot.child("Görev").getValue(String.class));
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Proje.this, android.R.layout.simple_spinner_item, gorevList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hata", "Okuma hatası", databaseError.toException());
            }
        });
        saveMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String selected = sp1.getSelectedItem().toString();
                    projeref.child(projekey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String id="";
                            for(DataSnapshot areaSnapshot : dataSnapshot.getChildren()){
                                if(areaSnapshot.child("Görev").getValue()== selected){
                                    id = areaSnapshot.getKey();
                                }
                            }
                            if(cb.isChecked()){
                                projeref.child(projekey).child(id).child("Yapıldı").setValue("Bitti");
                            }
                            else if (!cb.isChecked()){
                                projeref.child(projekey).child(id).child("Yapıldı").setValue("Henüz Bitmedi");
                            }
                            Toast.makeText(Proje.this,"Görev Kaydedildi",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            }
        });
        addMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = projeref.child(projekey).push().getKey();
                projeref.child(projekey).child(id).child("Görev").setValue(et4.getText().toString());
                Toast.makeText(Proje.this,"Görev Eklendi",Toast.LENGTH_LONG).show();
            }
        });
        //
        deleteMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String selected = sp1.getSelectedItem().toString();
                projeref.child(projekey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id ="";
                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                            if(areaSnapshot.child("Görev").getValue()==selected){
                                id = areaSnapshot.getKey();
                            }
                        }
                        projeref.child(projekey).child(id).removeValue();
                        Toast.makeText(Proje.this,"Seçilen Görev Silindi",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Proje.this,"Silinemedi",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projeref.child(projekey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String selected = sp1.getSelectedItem().toString();
                        String id ="";
                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                            if(areaSnapshot.child("Görev").getValue()==selected){
                                id = areaSnapshot.getKey();
                            }
                        }
                        et6.setText(dataSnapshot.child(id).child("Yapıldı").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        //
    }
}
