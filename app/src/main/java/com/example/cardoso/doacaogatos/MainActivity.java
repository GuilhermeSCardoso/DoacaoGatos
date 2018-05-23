package com.example.cardoso.doacaogatos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText loginEditText, senhaEntrarEditText;
    private Button entrarButton, cadastroButton;
    private DatabaseReference database;
    private static final int REQUISICAO_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        senhaEntrarEditText = (EditText) findViewById(R.id.senhaEntrarEditText);
        entrarButton = (Button) findViewById(R.id.entrarButton);
        cadastroButton = (Button) findViewById(R.id.cadastroButton);
        database = FirebaseDatabase.getInstance().getReference("usuarios");

        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String login = loginEditText.getText().toString();
                        String senha = senhaEntrarEditText.getText().toString();
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        Log.d("DADOS FIREBASE", usuario.getEmail() + usuario.getSenha());
                        if(login.equals("") || senha.equals("")) {
                            Toast.makeText(MainActivity.this, getString(R.string.login_senha_vazio), Toast.LENGTH_LONG).show();
                        } else {
                            if (login.equals(usuario.getEmail()) && senha.equals(usuario.getSenha())) {
                                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.erro_login), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUISICAO_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUISICAO_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("RESULTADO PERMISSÃO", "PERMITIDO!");
            } else {
                Log.d("RESULTADO PERMISSÃO", "NÃO PERMITIDO!");
            }
        }
    }
}
