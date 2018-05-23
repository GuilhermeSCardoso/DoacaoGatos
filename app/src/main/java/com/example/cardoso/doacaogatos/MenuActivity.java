package com.example.cardoso.doacaogatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button perfilButton;
    private Button adotarButton;
    private Button disponibilizarButton;
    private Button sairButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        perfilButton = (Button) findViewById(R.id.meuPerfilButton);
        adotarButton = (Button) findViewById(R.id.adotarButton);
        disponibilizarButton = (Button) findViewById(R.id.disponibilizarButton);
        sairButton = (Button) findViewById(R.id.sairButton);
        perfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, PerfilActivity.class);
                startActivity(i);
            }
        });
        adotarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        disponibilizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CadastroGatoActivity.class);
                startActivity(i);
            }
        });
        sairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
