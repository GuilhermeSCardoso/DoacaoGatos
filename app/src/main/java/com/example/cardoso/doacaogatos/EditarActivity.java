package com.example.cardoso.doacaogatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarActivity extends AppCompatActivity {

    private EditText nomeEditarEditText, telefoneEditarEditText, emailEditarEditText, senhaEditarEditText;
    private Button salvarEditarButton;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        nomeEditarEditText = (EditText) findViewById(R.id.nomeEditarEditText);
        telefoneEditarEditText = (EditText) findViewById(R.id.telefoneEditarEditText);
        emailEditarEditText = (EditText) findViewById(R.id.emailEditarEditText);
        senhaEditarEditText = (EditText) findViewById(R.id.senhaEditarEditText);
        salvarEditarButton = (Button) findViewById(R.id.salvarEditarButton);
        database = FirebaseDatabase.getInstance().getReference("usuarios");

        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                String nome = usuario.nome;
                String telefone = usuario.fone;
                String email = usuario.email;
                String senha = usuario.senha;
                nomeEditarEditText.setText(nome);
                telefoneEditarEditText.setText(telefone);
                emailEditarEditText.setText(email);
                senhaEditarEditText.setText(senha);
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

        salvarEditarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditarEditText.getText().toString();
                String telefone = telefoneEditarEditText.getText().toString();
                String email = emailEditarEditText.getText().toString();
                String senha = senhaEditarEditText.getText().toString();
                if (nome.equals("") || telefone.equals("") || email.equals("") || (senha.equals(""))) {
                    Toast.makeText(EditarActivity.this, getString(R.string.campos), Toast.LENGTH_LONG).show();
                } else {
                    cadastrar(view);
                }
            }
        });
    }

    private void cadastrar(View v) {
        String id = "dados";
        String nome = nomeEditarEditText.getText().toString();
        String telefone = telefoneEditarEditText.getText().toString();
        String email = emailEditarEditText.getText().toString();
        String senha = senhaEditarEditText.getText().toString();
        Usuario usuario = new Usuario(id, nome, telefone, email, senha);
        database.child(id).setValue(usuario);
        Toast.makeText(EditarActivity.this, getString(R.string.dados_alterados), Toast.LENGTH_LONG).show();
        finish();

    }

}
