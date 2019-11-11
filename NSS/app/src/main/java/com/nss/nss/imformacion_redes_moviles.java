package com.nss.nss;


import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
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

    private String roaming;
    private String MccAndMnc;
    private String phoneType = "Unknown";
    private String dataConected;
    private List<String> datosRM = new ArrayList<>();
    private ArrayAdapter datosRedes;
    private TelephonyManager tm;
    private ConnectivityManager con;
    private SignalStrength signal;
    private GridView listaDatos;
    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStatelistener;
    int mSignalStrength = 0;

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
        datosRM.add(tm.getNetworkOperatorName());
        datosRM.add("Tipo de red telefonica");
        datosRM.add(getTypeOfNetwork());
        datosRM.add("Tipo de red");
        datosRM.add(getTypeOfNetwork234());
        datosRM.add("Codigo de pais");
        datosRM.add(tm.getSimCountryIso());
        datosRM.add("mcc");
        MccAndMnc = tm.getNetworkOperator();
        datosRM.add(MccAndMnc.substring(0,3));
        datosRM.add("mnc");
        datosRM.add(MccAndMnc.substring(4,6));
        datosRM.add("Roamig");
        datosRM.add(getStateRoaming());
        datosRM.add("Phone type");
        datosRM.add(getPhoneType());
        datosRM.add("Data conected");
        datosRM.add(getDataConected());
        datosRM.add("Imei");
        datosRM.add(getnImei());
        datosRM.add("ip");
        datosRM.add(getMobileIPAddress());
        datosRM.add("Dbm");
        datosRM.add(String.valueOf(mSignalStrength));
    }

    public String getStateRoaming(){
        if(tm.isNetworkRoaming())
            return "False";
        else return "True";
    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "-";
    }



    public String getnImei(){
        int chekarPermiso = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_PHONE_STATE);
        if(chekarPermiso == PackageManager.PERMISSION_GRANTED)
            if(Build.VERSION.SDK_INT >= 26)
                return tm.getImei();
            else return tm.getDeviceId();
        else
            return "Desconocido";
    }


    public String getPhoneType(){
        switch (tm.getPhoneType()){
            case(TelephonyManager.PHONE_TYPE_CDMA):
                phoneType = " CDMA";break;
            case(TelephonyManager.PHONE_TYPE_GSM):
                phoneType = " GMS";break;
            case(TelephonyManager.PHONE_TYPE_NONE):
                phoneType = " NONE";break;
            case(TelephonyManager.PHONE_TYPE_SIP):
                phoneType = " SIP";break;
        }
        return  phoneType;
    }

    public String getDataConected(){
          int estadoDeRed = tm.getDataState();
          switch (estadoDeRed){
              case TelephonyManager.DATA_DISCONNECTED:
                  dataConected = "Desconectado";break;
              case TelephonyManager.DATA_CONNECTED:
               dataConected = "Conectado";

          }
        return dataConected;
    }

    /*this method return the type of the red in one var wich store the type of the red*/
    public String getTypeOfNetwork() {
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
        }
        throw new RuntimeException("New type of network");

    }

/*this method return type 2G,3G,4G*/
    public String getTypeOfNetwork234() {
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            return  "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
            return  "4G";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
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


    /*this code only are invoke when the activity begin*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        con = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //mPhoneStatelistener = new MyPhoneStateListener();
        //mTelephonyManager = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
        //mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
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


    class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm
            Toast.makeText(getContext(),"Cambio red"+mSignalStrength,Toast.LENGTH_LONG).show();

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
