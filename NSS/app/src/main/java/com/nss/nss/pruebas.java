package com.nss.nss;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pruebas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private  phone phoneListen;
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private TelephonyManager tm;
    private OnFragmentInteractionListener mListener;
    private String allInfo;/**variable que almacena imformacion sobre las redes moviles*/
    private String [] partInfo;/**Array que almacena cada elemento de allInfo*/
    private imformacionDispositivos info;/**objeto que usaremos para obtener imformacion del dispositivo*/
    private int asu;
    private int dbm;
    private Toast mensaje;

    public pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static pruebas newInstance(String param1, String param2) {
        pruebas fragment = new pruebas();
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
        info = new imformacionDispositivos();
        tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        phoneListen = new phone();
        tm.listen(phoneListen,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    class phone extends PhoneStateListener{

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            try {
                allInfo = signalStrength.toString();
                partInfo = allInfo.split(" ");
                if(info.getTypeOfNetwork234(tm)=="2G"){
                    asu = signalStrength.getGsmSignalStrength();
                    dbm = esAsu(asu);
                    enviarMensaje("2G"+asu+dbm);
                    ponerMedidaSpeed(dbm,asu);
                }
                /**en this point work in alcatel 5033A*/
                if(info.getTypeOfNetwork234(tm)=="3G"){
                    dbm = Integer.parseInt(partInfo[14]);//14
                    asu = esDbm(Integer.parseInt(partInfo[14]));//14
                    enviarMensaje("3G "+dbm+" "+asu);
                    ponerMedidaSpeed(dbm,asu);
                }
                if(info.getTypeOfNetwork234(tm)=="4G"){
                    dbm = Integer.parseInt(partInfo[8]);
                    asu = esDbm(Integer.parseInt(partInfo[8])-140);
                    Toast.makeText(getActivity(), "4G"+dbm+" "+asu+allInfo, Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                enviarMensaje(e.getMessage());
            }
        }
    }

    public void enviarMensaje(String say){
        mensaje = Toast.makeText(getActivity(),say,Toast.LENGTH_LONG);
        mensaje.setGravity(Gravity.CENTER,0,0);
        mensaje.show();
    }

    /**
     * metodo el cual recibe como parametro un int el cual es el dbm y
     * regresa un asu
     */
    public int esAsu(int asu){
        int dbm = ((2*asu) -113);
        return dbm;
    }

    public int esDbm(int dbm){
        int asu = (dbm+120);
        return asu;
    }

    public void ponerMedidaSpeed(int pasu, int pdbm){
      speedometer.speedTo(pasu);
      speedDeluxe.speedTo(pdbm);
    }


    @Override
    public void onResume() {
        super.onResume();
        tm.listen(phoneListen,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        speedometer = vista.findViewById(R.id.speedView);
        speedDeluxe = vista.findViewById(R.id.speedDeluxe);
        speedometer.setWithTremble(false);
        speedDeluxe.setWithTremble(false);
        speedometer.setUnitUnderSpeedText(true);
        speedDeluxe.setUnitUnderSpeedText(true);
        speedometer.setUnit("dbm");
        speedDeluxe.setUnit("asu");
        speedometer.setMinSpeed(-113);
        speedometer.setMaxSpeed(-51);
        speedDeluxe.setMinSpeed(0);
        speedDeluxe.setMaxSpeed(31);

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tm.listen(phoneListen,PhoneStateListener.LISTEN_NONE);
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
