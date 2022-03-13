package com.nss.nss.ui.pruebas;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;
import com.nss.nss.R;
import com.nss.nss.ui.imformacion_device.TelefonoMedida;

public class pruebas extends Fragment {

    private TelefonoMedida phoneListen;
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private TelephonyManager tm;
    public static Button btnIniciarPrueba;
    private int escucharTelefono = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;
    private Typeface letra;

    public pruebas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        letra=Typeface.createFromAsset(requireContext().getAssets(),"fuentes/TitilliumWeb-Bold.ttf");
    }


    private void btnPrueba() {
        if (btnIniciarPrueba.getText().toString().equalsIgnoreCase("Detener"))
            btnIniciarPrueba.setText("Iniciar prueba");
        else {
            btnIniciarPrueba.setText("Detener");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        tm.listen(phoneListen, escucharTelefono);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        btnIniciarPrueba = vista.findViewById(R.id.btnIniciarPrueba);
        btnIniciarPrueba.setTypeface(letra);
        tm = (TelephonyManager) requireContext().getSystemService(Context.TELEPHONY_SERVICE);
        speedometer = vista.findViewById(R.id.speedView);
        speedDeluxe = vista.findViewById(R.id.speedDeluxe);
        phoneListen = new TelefonoMedida(getActivity(), speedometer, speedDeluxe);
        tm.listen(phoneListen, escucharTelefono);
        btnIniciarPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneListen.setPermitirGirar(true);
                btnPrueba();
            }
        });
        inicializarValoresSpeed();
        return vista;
    }

    private void inicializarValoresSpeed() {
        speedometer.setWithTremble(false);
        speedDeluxe.setWithTremble(false);
        speedometer.setUnitUnderSpeedText(true);
        speedDeluxe.setUnitUnderSpeedText(true);
        speedometer.setUnit("dbm");
        speedDeluxe.setUnit("asu");
        speedometer.setMinSpeed(-120);
        speedometer.setMaxSpeed(-51);
        speedDeluxe.setMinSpeed(0);
        speedDeluxe.setMaxSpeed(63);
        speedometer.setTickNumber(4);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tm.listen(phoneListen, PhoneStateListener.LISTEN_NONE);
    }
    
}
