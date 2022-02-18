package com.nss.nss.ui.graph;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.nss.nss.R;


public class Graph extends Fragment {

    private GraficaLibrery graficaDbm;
    private GraphView graphViewDbm;


    public Graph() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafica_medidas, container, false);
        graphViewDbm = view.findViewById(R.id.graph);
        graficaDbm = new GraficaLibrery(graphViewDbm, getContext());
        graficaDbm.inicializarGraphView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
