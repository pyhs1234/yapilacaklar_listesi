package com.example.proje;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final TextView email = (TextView)findViewById(R.id.textView8);
        final TextView sifre = (TextView)findViewById(R.id.textView9);
        final EditText email1 = (EditText)findViewById(R.id.editText2);
        final EditText sifre1 = (EditText)findViewById(R.id.sifre);
        Button giris = (Button)findViewById(R.id.giris);
        Button kaydol = (Button)findViewById(R.id.kaydol);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = email1.getText().toString();
                String b = sifre1.getText().toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hata");
                builder.setMessage("Hatalı Kullanıcı Adı veya Şifre");
                if(a.equals("admin")&&b.equals("admin")){
                    Intent giris1 = new Intent(MainActivity.this, AdminGiris.class);
                    startActivity(giris1);
                }
                else{
                    mAuth.signInWithEmailAndPassword(a,b).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                                startActivity(new Intent(getApplicationContext(),AdminGiris.class));
                            else
                                builder.show();
                        }
                    });
                }
            }
        });
        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kaydol1= new Intent(MainActivity.this, Kaydol.class);
                startActivity(kaydol1);
            }
        });
    }
}
