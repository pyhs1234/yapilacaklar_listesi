package com.example.yaplacaklarlisteuygulamas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitOl extends AppCompatActivity {

    EditText mFullname,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        mFullname = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();


        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email Boş!!!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Şifre Boş!!!");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Şifre 6 hane ya da daha büyük olmalı !!!");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(KayitOl.this, "Kullanıcı Oluşturuldu!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(KayitOl.this, "Hata!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });


    }
}
