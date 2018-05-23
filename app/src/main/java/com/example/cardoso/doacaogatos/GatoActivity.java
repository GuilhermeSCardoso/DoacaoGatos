package com.example.cardoso.doacaogatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class GatoActivity extends AppCompatActivity {

    private ImageView fotoGato;
    private TextView descricaoTextView;
    private Button ligarButton;
    private DatabaseReference database;
    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);
        fotoGato = (ImageView) findViewById(R.id.fotoGatoImageView);
        descricaoTextView = (TextView) findViewById(R.id.descricaoTextView);
        ligarButton = (Button) findViewById(R.id.ligarButton);
        database = FirebaseDatabase.getInstance().getReference("gatos");
        storage = FirebaseStorage.getInstance().getReference("fotogato");
        Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/doacaogatos-dadde.appspot.com/o/fotogato?alt=media&token=ae5f1b85-9267-465d-a47c-8730d17e9aa2")
                .into(fotoGato);
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Gato gato = dataSnapshot.getValue(Gato.class);
                String descricao = gato.descricao;
                descricaoTextView.setText(descricao);
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

}
