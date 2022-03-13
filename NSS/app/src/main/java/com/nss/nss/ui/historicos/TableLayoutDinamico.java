package com.nss.nss.ui.historicos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nss.nss.R;

import java.util.ArrayList;

public class TableLayoutDinamico {


    private TableLayout myTabla;
    private Context context;
    private TextView textView;
    private TableRow renglon;
    private Typeface letra;


    public TableLayoutDinamico(TableLayout tabla, Context ctx) {
        myTabla = tabla;
        context = ctx;
        letra = Typeface.createFromAsset(context.getAssets(), "fuentes/TitilliumWeb-SemiBold.ttf");
    }

    private void agregarRenglon() {
        renglon = new TableRow(context);
        renglon.setBackgroundColor(Color.RED);
    }

    public void agregarCabezeras(String[] cabecera) {
        agregarRenglon();
        for (String c : cabecera)
            crearCeldaCabezera(c);
        myTabla.addView(renglon);
    }

    private void crearCelda(String texto) {
        textView = new TextView(context);
        textView.setPadding(11, 7, 10, 11);
        textView.setBackgroundResource(R.drawable.textview_border);
        textView.setTypeface(letra);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(R.color.colorNns1));
        textView.setText(texto);
        renglon.addView(textView);
        renglon.setBackgroundColor(context.getResources().getColor(R.color.colorNnsAuxiliar3));
    }


    private void crearCeldaCabezera(String texto) {
        textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.textview_border);
        textView.setTypeface(letra);
        textView.setTextSize(15);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        textView.setText(texto);
        LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,100);
        textView.setLayoutParams(parms_legen_layout);
        renglon.addView(textView);
    }


    public void agregarRegistrosTable(int total, ArrayList<String> registros) {
        int contador = 0;
        for (int i = 0; i < total; i++) {
            agregarRenglon();
            for (int j = 0; j < 7; j++) {
                crearCelda(registros.get(contador));
                contador++;
            }
            myTabla.addView(renglon);
        }
    }


}
