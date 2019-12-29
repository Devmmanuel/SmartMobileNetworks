package com.nss.nss;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;


import com.nss.nss.ui.main.SectionsPagerAdapter;

public class RedesMovilesActivity extends AppCompatActivity implements
        historicos_pruebas.OnFragmentInteractionListener, imformacion_redes_moviles.OnFragmentInteractionListener,
        pruebas.OnFragmentInteractionListener, grafica_medidas.OnFragmentInteractionListener {

    private TabLayout tabs;
    private int[] imagenes_tabs = {
            R.mipmap.imformacion_foreground,
            R.mipmap.grafica_foreground,
            R.mipmap.historicos_foreground,
            R.mipmap.pruebas_foreground
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_moviles);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        ponerIconos();
    }

    public void ponerIconos() {
        for (int i = 0; i < imagenes_tabs.length; i++)
            tabs.getTabAt(i).setIcon(imagenes_tabs[i]);

    }

    /**
     * metodo el cual destrulle la activity de redes moviles cuando presionamos el boton de retroseso
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            super.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}