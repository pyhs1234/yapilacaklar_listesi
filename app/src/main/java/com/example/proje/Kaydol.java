package com.example.proje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Kaydol extends Activity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("kullanıcı");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaydol);
        TextView tw = (TextView)findViewById(R.id.textView3);
        TextView tw2 = (TextView)findViewById(R.id.textView4);
        TextView sifre = (TextView)findViewById(R.id.textView5);
        TextView eposta = (TextView)findViewById(R.id.textView10);
        final EditText ad = (EditText)findViewById(R.id.editText4);
        final EditText soyad = (EditText)findViewById(R.id.editText5);
        final EditText sifre1 = (EditText)findViewById(R.id.editText3);
        final EditText eposta1 = (EditText)findViewById(R.id.editText7);
        Button kaydol2 = (Button)findViewById(R.id.kayit);
        kaydol2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email =eposta1.getText().toString().trim();
                String sifre =sifre1.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id = mAuth.getUid();
                            myRef.child(id).child("ad").setValue(ad.getText().toString());
                            myRef.child(id).child("soyad").setValue(soyad.getText().toString());
                            Toast.makeText(Kaydol.this, "Kullanıcı Oluşturuldu!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Kaydol.this, "Hata!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
