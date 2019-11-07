package com.nss.nss;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


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

    ListView listaDatos;
    String[] datos ={"Operador:","Intensidad","Tipo de red","Tipo de red telefonica","Ip","Imei","Mac",
            "Mascara de subred","MCC","MNC","Velocidad"};

String [] datosSim;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public imformacion_redes_moviles() {
        // Required empty public constructor
    }



    public void getImformationRedesMoviles(){
        /*this class are for get imformation about the state of the telephone*/
        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        ConnectivityManager con = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        datosSim = new String[10];
        datosSim[0]=tm.getSimOperator();
        datosSim[2]=getTypeOfNetwork();
        Toast.makeText(getContext(),datos[2],Toast.LENGTH_SHORT).show();
    }


    /*this method return the type of the red in one var wich store the type of the red*/
    public String getTypeOfNetwork(){

        TelephonyManager tm2 = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm2.getNetworkType()){
            case TelephonyManager.NETWORK_TYPE_1xRTT: return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA: return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE: return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD: return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0: return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A: return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B: return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS: return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA: return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA: return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP: return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA: return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN: return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE: return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS: return "UMTS";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN: return "Unknown";
        }
        throw new RuntimeException("New type of network");

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*this code is only for initit componets*/
        Toast.makeText(getContext(),"hell",Toast.LENGTH_SHORT).show();

        View vista = inflater.inflate(R.layout.fragment_imformacion_redes_moviles,container,false);
     listaDatos = vista.findViewById(R.id.listaDeDatosRedes);

        ArrayAdapter datosRedes = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_activated_1, datos);
        listaDatos.setAdapter(datosRedes);
       getImformationRedesMoviles();


        // Inflate the layout for this fragment
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
