package com.nss.nss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nss.nss.util.SqlManager;


public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnMenuRedesMoviles;
    private ImageButton btnWifi;
    private TextView txtWifi;
    private TextView txtRedesMoviles;
    private String urlFuente = "fuentes/TitilliumWeb-Black.ttf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMenuRedesMoviles = findViewById(R.id.btnMovil);
        btnWifi = findViewById(R.id.btnWifi);
        txtRedesMoviles = findViewById(R.id.txtMovil);
        txtWifi = findViewById(R.id.txtWifi);
        txtRedesMoviles.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));
        txtWifi.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));

        btnMenuRedesMoviles.setOnClickListener(this);
        btnWifi.setOnClickListener(this);
        darPermisosApp();
    }

    private void darPermisosApp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    (this, new String[]
                                    {Manifest.permission.READ_PHONE_STATE,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            super.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        if (v == btnMenuRedesMoviles) {
            Intent intento = new Intent(InicioActivity.this, RedesMovilesActivity.class);
            startActivity(intento);
        }
        if (v == btnWifi) {
            Intent intento = new Intent(InicioActivity.this, SqlManager.class);
            startActivity(intento);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_redes_moviles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAcercaDe) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_acerca_de);
            builder.setMessage(R.string.contenido_acerca_de);
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}