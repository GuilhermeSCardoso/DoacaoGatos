package com.example.cardoso.doacaogatos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class CadastroGatoActivity extends AppCompatActivity {

    private Button tirarFotoGatoButton, salvarGatoButton;
    private ImageView fotoGatoCadastroImageView;
    private EditText descricaoEditText;
    private DatabaseReference database;
    private StorageReference storage;
    private static final int REQUISICAO_GPS = 4;
    private static final int REQUISICAO_CAMERA = 1;
    public static final int IMAGEM_GALERIA = 2;
    private LocationManager locationManager;
    private Location localizacaoAtual = null;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            localizacaoAtual = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_gato);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        tirarFotoGatoButton = (Button) findViewById(R.id.tirarFotoGatoButton);
        salvarGatoButton = (Button) findViewById(R.id.salvarGatoButton);
        fotoGatoCadastroImageView = (ImageView) findViewById(R.id.fotoGatoCadastroImageView);
        descricaoEditText = (EditText) findViewById(R.id.descricaoEditText);
        database = FirebaseDatabase.getInstance().getReference("gatos");
        tirarFotoGatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFotoGato();
            }
        });
        tirarFotoGatoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, getString(R.string.selecione_foto)), IMAGEM_GALERIA);
                return false;
            }
        });
        salvarGatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descricao = descricaoEditText.getText().toString();
                if (descricao.equals("")) {
                    Toast.makeText(CadastroGatoActivity.this, getString(R.string.descricao_vazia), Toast.LENGTH_LONG).show();
                } else {
                    cadastrarGato();
                    Intent i = new Intent(CadastroGatoActivity.this, GatoActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void tirarFotoGato() {
        Intent tirarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tirarFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(tirarFotoIntent, REQUISICAO_CAMERA);
        }
    }

    private void cadastrarGato() {
        String id = "gato";
        String descricao = descricaoEditText.getText().toString();
        if (localizacaoAtual == null) {
            //LatLng localizacao = new LatLng(-23.5631338, -46.6543286);
            Gato gato = new Gato(id, descricao);
            database.child(id).setValue(gato);
            Toast.makeText(CadastroGatoActivity.this, getString(R.string.cadastro_realizado), Toast.LENGTH_LONG).show();
            finish();
        } else {
            double latitude2 = localizacaoAtual.getLatitude();
            double longitude2 = localizacaoAtual.getLongitude();
            LatLng localizacao = new LatLng(latitude2, longitude2);
            Gato gato = new Gato(id, descricao);
            database.child(id).setValue(gato);
            Toast.makeText(CadastroGatoActivity.this, getString(R.string.cadastro_realizado), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUISICAO_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    fotoGatoCadastroImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    fotoGatoCadastroImageView.setImageBitmap(foto);
                    storage = FirebaseStorage.getInstance().getReference("fotogato");
                    fotoGatoCadastroImageView.setDrawingCacheEnabled(true);
                    fotoGatoCadastroImageView.buildDrawingCache();
                    foto = fotoGatoCadastroImageView.getDrawingCache();
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
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(CadastroGatoActivity.this, getString(R.string.alerta_gps), Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUISICAO_GPS);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUISICAO_GPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
                else {
                    Toast.makeText(this, getString(R.string.alerta_gps), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

}
