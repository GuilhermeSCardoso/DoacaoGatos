package com.example.cardoso.doacaogatos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText nomeEditText, telefoneEditText, emailEditText, senhaEditText;
    private Button salvarButton;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        telefoneEditText = (EditText) findViewById(R.id.telefoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        senhaEditText = (EditText) findViewById(R.id.senhaEditText);
        salvarButton = (Button) findViewById(R.id.salvarButton);
        database = FirebaseDatabase.getInstance().getReference("usuarios");
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditText.getText().toString();
                String telefone = telefoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                if (nome.equals("")||telefone.equals("")
                        ||email.equals("")||senha.equals("")) {
                    Toast.makeText(CadastroActivity.this, getString(R.string.campos), Toast.LENGTH_LONG).show();
                } else {
                    cadastrar(view);
                }
            }
        });
    }

    private void cadastrar(View v) {
        String id = "dados";
        String nome = nomeEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        Usuario usuario = new Usuario(id, nome, telefone, email, senha);
        database.child(id).setValue(usuario);
        Toast.makeText(CadastroActivity.this, getString(R.string.cadastro_realizado), Toast.LENGTH_LONG).show();
        finish();
    }

}
