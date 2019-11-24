package com.nss.nss;


import android.telephony.TelephonyManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private String MccAndMnc;
    private String phoneType = "Unknown";
    private String dataConected;
    private List<String> datosRM = new ArrayList<>();
    private ArrayAdapter datosRedes;
    private TelephonyManager tm;
    private ConnectivityManager con;
    private GridView listaDatos;
    private int mSignalStrength = 0;
    private imformacionDispositivos info;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public imformacion_redes_moviles() {
        // Required empty public constructor

    }


    public void getImformationRedesMoviles() {
        /*this method are for get imformation about the state of the telephone
         * this method get the date what after sshow in the GriedView*/
        datosRM.add("Operaror");
        datosRM.add(info.getOperator(tm));
        datosRM.add("Tipo de red telefonica");
        datosRM.add(info.getTypeOfNetwork(tm));
        datosRM.add("Tipo de red");
        datosRM.add(info.getTypeOfNetwork234(tm));
        datosRM.add("Codigo de pais");
        datosRM.add(tm.getSimCountryIso());
        datosRM.add("mcc");
        MccAndMnc = tm.getNetworkOperator();
        datosRM.add(MccAndMnc.substring(0, 3));
        datosRM.add("mnc");
        datosRM.add(MccAndMnc.substring(4, 6));
        datosRM.add("Roamig");
        datosRM.add(info.getStateRoaming(tm));
        datosRM.add("Phone type");
        datosRM.add(info.getPhoneType(tm));
        datosRM.add("Data conected");
        datosRM.add(info.getDataConected(tm));
        datosRM.add("Imei");
        datosRM.add(info.getnImei(getContext(), tm));
        datosRM.add("ip");
        datosRM.add(info.getMobileIPAddress());
        datosRM.add("Dbm");
        datosRM.add(String.valueOf(mSignalStrength));
        datosRM.add("Mac");
        datosRM.add(info.getMacAddress());

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
        info = new imformacionDispositivos();
        tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        con = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        datosRedes = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, datosRM);
        getImformationRedesMoviles();


    }

    /*this code is invoke whe you move between first and last fragment*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*this code is only for initit componets*/
        /*this code show when you */
        View vista = inflater.inflate(R.layout.fragment_imformacion_redes_moviles, container, false);

        listaDatos = vista.findViewById(R.id.FIRM_gridViewDatos);
        listaDatos.setAdapter(datosRedes);
        // Inflate the layout for this fragment
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
