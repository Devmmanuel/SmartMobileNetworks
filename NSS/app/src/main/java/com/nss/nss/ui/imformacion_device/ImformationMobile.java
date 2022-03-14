package com.nss.nss.ui.imformacion_device;


import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nss.nss.R;
import java.util.ArrayList;
import java.util.List;


public class ImformationMobile extends Fragment {


    private List<String> ListaDatosRM = new ArrayList<>();
    private RecyclerView recyclerViewDatos;
    AdaptadorImformationDevice adaptador;
    private ImformacionDispositivos info;
    private TelephonyManager tm;
    private TelefonoMedida telefonoMedida;
    private int escucharTelefono = PhoneStateListener.LISTEN_DATA_ACTIVITY | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;


    public ImformationMobile() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tm = (TelephonyManager) requireContext().getSystemService(Context.TELEPHONY_SERVICE);
        info = new ImformacionDispositivos(getContext());
        adaptador = new AdaptadorImformationDevice(info.getImformationRedesMoviles());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_imformacion_redes_moviles, container, false);
        recyclerViewDatos = vista.findViewById(R.id.recycler_datos);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerViewDatos.setLayoutManager(layoutManager);
        recyclerViewDatos.setAdapter(adaptador);
        telefonoMedida = new TelefonoMedida(recyclerViewDatos, getActivity(), ListaDatosRM);
        tm.listen(telefonoMedida, escucharTelefono);

        return vista;
    }

    private void setUpUi() {

    }


}
