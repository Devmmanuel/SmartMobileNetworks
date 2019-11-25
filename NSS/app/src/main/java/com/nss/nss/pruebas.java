package com.nss.nss;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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


    private phone phoneListen;
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private TelephonyManager tm;
    private OnFragmentInteractionListener mListener;
    private String allInfo;
    /**
     * variable que almacena imformacion sobre las redes moviles
     */
    private String[] partInfo;
    /**
     * Array que almacena cada elemento de allInfo
     */
    private imformacionDispositivos info;
    /**
     * objeto que usaremos para obtener imformacion del dispositivo
     */
    private int asu;
    private int dbm;
    private Toast mensaje;
    private boolean permitirGirar = false;
    private Button btnIniciarPrueba;
    private String[] medidas = new String[2];
    private AdminSql sql;
    private SQLiteDatabase db;
    private DbmAsu graf;



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
        graf = new DbmAsu();
        tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        phoneListen = new phone();
        tm.listen(phoneListen, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }


    class phone extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            try {
                allInfo = signalStrength.toString();
                partInfo = allInfo.split(" ");
                /*metodo que se ejecuta cuando el tipo de red es 2G*/
                if (info.getTypeOfNetwork234(tm) == "2G") {
                    asu = signalStrength.getGsmSignalStrength();
                    dbm = esAsu(asu);
                    enviarMensaje("2G" + asu + dbm);
                    ponerMedidaSpeed(dbm, asu);
                }
                /**en this point work in alcatel 5033A este metod se ejecuta cuando la red es 3G*/
                if (info.getTypeOfNetwork234(tm) == "3G") {
                    /**codigo que se ejecuta cuando la version de android es 7.0*/
                    if (Build.VERSION.RELEASE.equals("7.0")) {
                        //asu=Integer.parseInt(partInfo[1]);
                        //dbm=esAsu(asu);
                        medidas = getSignalStrength(getActivity());
                        dbm = Integer.parseInt(medidas[0]);
                        asu = Integer.parseInt(medidas[1]);
                    } else {
                        dbm = Integer.parseInt(partInfo[14]);//14
                        asu = esDbm(Integer.parseInt(partInfo[14]));//14
                    }
                    enviarMensaje("3G " + dbm + " " + asu);
                    ponerMedidaSpeed(dbm, asu);
                }
                /**este metodo se ejecuta cuando el tipo de red es 4G* */
                if (info.getTypeOfNetwork234(tm) == "4G") {
                    dbm = Integer.parseInt(partInfo[9]);
                    asu = (Integer.parseInt(partInfo[2]));//140
                    Toast.makeText(getActivity(), "4G" + dbm + " " + asu, Toast.LENGTH_SHORT).show();
                    ponerMedidaSpeed(dbm, asu);
                }
                if(btnIniciarPrueba.getText().toString().equalsIgnoreCase("DETENER")){
                    insertar(dbm,asu);
                }
                graf.setAsu(asu);
                graf.setDbm(dbm);
            } catch (Exception e) {
                enviarMensaje(e.getMessage());
                //dbm = esAsu(Integer.parseInt(partInfo[1]));
                //enviarMensaje(String.valueOf(dbm));
                Log.w("MENSAJE",e.getMessage());
            }
        }


    }

    /*este metodo obtiene el dbm Level en  en diferentes tipos de redes**/
    private String[] getSignalStrength(Context context) throws SecurityException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String[] strength = new String[2];
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
        if (cellInfos != null) {
            for (int i = 0; i < cellInfos.size(); i++) {
                if (cellInfos.get(i).isRegistered()) {
                    if (cellInfos.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfos.get(i);
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                        strength[0] = String.valueOf(cellSignalStrengthWcdma.getDbm() - 31);
                        strength[1] = String.valueOf(cellSignalStrengthWcdma.getAsuLevel() - 16);
                    } else if (cellInfos.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm cellInfogsm = (CellInfoGsm) cellInfos.get(i);
                        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                        strength[0] = String.valueOf(cellSignalStrengthGsm.getDbm() - 31);
                        strength[1] = String.valueOf(cellSignalStrengthGsm.getAsuLevel() - 16);
                    } else if (cellInfos.get(i) instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfos.get(i);
                        CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                        strength[0] = String.valueOf(cellSignalStrengthLte.getDbm() - 31);
                        strength[1] = String.valueOf(cellSignalStrengthLte.getAsuLevel() - 16);
                    } else if (cellInfos.get(i) instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfos.get(i);
                        CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                        strength[0] = String.valueOf(cellSignalStrengthCdma.getDbm() - 31);
                        strength[1] = String.valueOf(cellSignalStrengthCdma.getAsuLevel() - 16);

                    }
                }
            }
        } else Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        return strength;
    }

    /*este metodo regresa la version del dispositivo*/
    public String androiVersion() {
        String release = Build.VERSION.RELEASE;
        return release;
    }

    public String obtenerFecha() {
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);
        return salida;
    }

    /*metodo usado para realizacion de pruebas usando un Toast*/
    public void enviarMensaje(String say) {
        mensaje = Toast.makeText(getActivity(), say, Toast.LENGTH_LONG);
        mensaje.setGravity(Gravity.CENTER, 0, 0);
        mensaje.show();
    }

    public void btnPrueba() {
        if (btnIniciarPrueba.getText().toString().equalsIgnoreCase("Detener"))
            btnIniciarPrueba.setText("Iniciar prueba");
        else
            btnIniciarPrueba.setText("Detener");
    }

    /**
     * @return int
     * metodo el cual recibe como parametro un int el cual es el asu y
     * regresa un dbm
     */
    public int esAsu(int asu) {
        int dbm = ((2 * asu) - 113);
        return dbm;
    }

    public int esDbm(int dbm) {
        int asu = (dbm + 120);
        return asu;
    }

    /***
     *
     * @param pasu
     * @param pdbm
     * metodo el cual poner el velocimetro en las medidas asu y dbm que se estan recibiendo
     * actualmente
     */
    public void ponerMedidaSpeed(int pasu, int pdbm) {
        if (permitirGirar) {
            speedometer.speedTo(pasu);
            speedDeluxe.speedTo(pdbm);
        }

    }

    public void insertar(int iDbm, int iAsu) {
        try {
            sql = new AdminSql(getActivity(), "mydb", null, 1);
            db = sql.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("fecha", obtenerFecha());
            registro.put("dbm", iDbm);
            registro.put("asu", iAsu);
            registro.put("pais", tm.getSimCountryIso());
            registro.put("tipo_de_red", info.getTypeOfNetwork234(tm));
            registro.put("tipo_de_red_telefonica", info.getTypeOfNetwork(tm));
            db.insert("historicosRedesMoviles", null, registro);
            Toast.makeText(getActivity(), "Se cargaron los registros correctamente", Toast.LENGTH_SHORT).show();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void enviarNotificacion(String mensajeNotificacion){
        NotificationManager notif = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(getActivity())
                .setContentTitle("Cambio de red").setContentText(mensajeNotificacion)
                .setContentTitle("Simple notificacion").setSmallIcon(R.drawable.ic_launcher_background).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }

    @Override
    public void onResume() {
        super.onResume();
        tm.listen(phoneListen, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        speedometer = vista.findViewById(R.id.speedView);
        speedDeluxe = vista.findViewById(R.id.speedDeluxe);
        btnIniciarPrueba = vista.findViewById(R.id.btnIniciarPrueba);
        btnIniciarPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permitirGirar = true;
                btnPrueba();
            }
        });
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
        tm.listen(phoneListen, PhoneStateListener.LISTEN_NONE);
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
