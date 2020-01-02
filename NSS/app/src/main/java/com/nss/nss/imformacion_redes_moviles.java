package com.nss.nss;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link imformacion_redes_moviles.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link imformacion_redes_moviles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class imformacion_redes_moviles extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /**
     * var to store content of the data
     */


    private List<String> datosRM = new ArrayList<>();
    private ArrayAdapter datosRedes;
    private GridView listaDatos;
    private imformacionDispositivos info;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public imformacion_redes_moviles() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment imformacion_redes_moviles.
     */
    // TODO: Rename and change types and number of parameters
    public static imformacion_redes_moviles newInstance(String param1, String param2) {
        imformacion_redes_moviles fragment = new imformacion_redes_moviles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /*this code only are invoke when the activity begin*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        info = new imformacionDispositivos(getActivity());
        datosRedes = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, datosRM);
        info.getImformationRedesMoviles(datosRM);
    }

    /*this code is invoke whe you move between first and last fragment*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_imformacion_redes_moviles, container, false);

        listaDatos = vista.findViewById(R.id.FIRM_gridViewDatos);

        listaDatos.setAdapter(datosRedes);
        actualizarGriedView();
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void actualizarGriedView() {
        datosRedes.clear();
        info.getImformationRedesMoviles(datosRM);
        Log.w("RR", "Actualizando");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
