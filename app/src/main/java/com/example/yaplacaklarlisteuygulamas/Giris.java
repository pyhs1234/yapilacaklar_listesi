package com.example.yaplacaklarlisteuygulamas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Giris extends AppCompatActivity {

    TextView goRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        goRegisterBtn = findViewById(R.id.createText);


        goRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kaydol1= new Intent(Giris.this, KayitOl.class);
                startActivity(kaydol1);
            }
        });



    }
}
