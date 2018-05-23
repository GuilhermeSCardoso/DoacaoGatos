package com.example.cardoso.doacaogatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class PerfilActivity extends AppCompatActivity {

    private TextView nomeTextView, telefoneTextView, emailTextView;
    private Button editarButton;
    private ImageButton fotoButton;
    private DatabaseReference database;
    private StorageReference storage;
    private static final int REQUISICAO_CAMERA = 1;
    public static final int IMAGEM_GALERIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        telefoneTextView = (TextView) findViewById(R.id.telefoneTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        editarButton = (Button) findViewById(R.id.editarButton);
        fotoButton = (ImageButton) findViewById(R.id.fotoButton);
        database = FirebaseDatabase.getInstance().getReference("usuarios");
        storage = FirebaseStorage.getInstance().getReference("fotousu");
        atualizarCamposVisuais();
        carregarFoto();
        editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilActivity.this, EditarActivity.class);
                startActivity(i);
            }
        });
        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });
        fotoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, getString(R.string.selecione_foto)), IMAGEM_GALERIA);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarCamposVisuais();
    }

    private void atualizarCamposVisuais() {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                String nome = usuario.getNome();
                String telefone = usuario.getFone();
                String email = usuario.getEmail();
                nomeTextView.setText(nome);
                telefoneTextView.setText(telefone);
                emailTextView.setText(email);
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

    private void tirarFoto() {
        Intent tirarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tirarFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(tirarFotoIntent, REQUISICAO_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUISICAO_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("FOTO", "TIROU FOTO");
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    fotoButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    fotoButton.setImageBitmap(foto);
                    storage = FirebaseStorage.getInstance().getReference("fotousu");
                    fotoButton.setDrawingCacheEnabled(true);
                    fotoButton.buildDrawingCache();
                    foto = fotoButton.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    foto.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] img = baos.toByteArray();
                    UploadTask ut = storage.putBytes(img);
                    ut.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri uri = taskSnapshot.getDownloadUrl();
                        }
                    });
                } else {
                    Log.d("FOTO", "NÃO TIROU FOTO");
                }
        }
    }

    public void carregarFoto() {
        Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/doacaogatos-dadde.appspot.com/o/fotousu?alt=media&token=ffbf1d6e-31cf-48dd-bd8f-610d6226767e")
                .into(fotoButton);
    }

}
