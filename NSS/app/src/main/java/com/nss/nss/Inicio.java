package com.nss.nss;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class Inicio extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnMenuRedesMoviles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMenuRedesMoviles = findViewById(R.id.btnMovil);
        btnMenuRedesMoviles.setOnClickListener(this);
        darPermisosApp();
    }

    /*este metodo sirve para dar permisos a las aplicacion ya que si no le damos los permios suficientes a la aplicacion no pedemos
    * usar algunos metodos de PhoneManeger*/
    public void darPermisosApp(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions
                    (this,new String[]{Manifest.permission.READ_PHONE_STATE},
                            123);
    }

    @Override
    public void onClick(View v) {
        Intent intento = new Intent(Inicio.this,RedesMovilesActivity.class);
        startActivity(intento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acerca_de) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_acerca_de);
            builder.setMessage(R.string.contenido_acerca_de);
            builder.setPositiveButton(R.string.txt_cerrar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            Dialog dialogo = builder.create();
            dialogo.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}