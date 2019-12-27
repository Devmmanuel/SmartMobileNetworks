package com.nss.nss;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class CalendarioDialog {

    private Calendar calendario;
    private DatePickerDialog seleccionarFecha;
    private Context context;
    private int dia;
    private int mes;
    private int anio;

    public CalendarioDialog(Context ctx, final EditText text) {
        context = ctx;
        obtenerFecha();
        seleccionarFecha = new DatePickerDialog(context, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        text.setText(day + "/" + (month + 1) + "/" + cambiarAnio(year));
                    }

                }, anio, mes, dia);
        seleccionarFecha.show();

    }

    public String cambiarAnio(int yy) {
        return String.valueOf(yy).substring(2, 4);
    }

    public void obtenerFecha() {
        calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anio = calendario.get(Calendar.YEAR);
    }


}
