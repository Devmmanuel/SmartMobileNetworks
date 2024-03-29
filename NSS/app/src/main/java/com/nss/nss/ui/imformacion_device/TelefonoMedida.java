package com.nss.nss.ui.imformacion_device;


import android.content.Context;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nss.nss.ui.pruebas.OnNetworkChange;
import com.nss.nss.ui.pruebas.pruebas;
import com.nss.nss.util.AdminSql;
import com.nss.nss.util.NotificationHelpener;

import java.util.List;

public class TelefonoMedida extends PhoneStateListener {

    private String allInfo;
    private String[] partInfo;
    private int asu;
    private int dbm;
    private ImformacionDispositivos info;
    private AdminSql adminSql;
    private String[] medidas = new String[2];
    private boolean permitirGirar;
    private NotificationHelpener notificacionHelpe;
    private String tituloMensajeNotificacion = "La red cambio";
    private String nombreDeFragment;
    private int lastXpoint = 1;


    /*variables usadas con instanciaos desde el fragmente de pruebas*/
    private OnNetworkChange onNetworkChange;
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private RecyclerView recycler_view_data;
    private List<String> listDatosRm;
    private LineGraphSeries<DataPoint> series;


    public void setPermitirGirar(boolean permitirGirar) {
        this.permitirGirar = permitirGirar;
    }


    /*fragment de pruebas*/
    public TelefonoMedida(Context context, SpeedView sp, DeluxeSpeedView dx,OnNetworkChange onNetworkChangep) {
        nombreDeFragment = "pruebas";
        speedDeluxe = dx;
        speedometer = sp;
        onNetworkChange=onNetworkChangep;
        notificacionHelpe = new NotificationHelpener(context);
        info = new ImformacionDispositivos(context);
        adminSql = new AdminSql(context, "mydb", null, 1);
    }

    /* fragment de imformacion*/
    public TelefonoMedida(RecyclerView datosRedes, Context context, List<String> datosRM) {
        nombreDeFragment = "imformacion";
        recycler_view_data = datosRedes;
        listDatosRm = datosRM;
        info = new ImformacionDispositivos(context);

    }

    public TelefonoMedida(LineGraphSeries<DataPoint> dbm, Context context) {
        this.series = dbm;
        nombreDeFragment = "grafica";
        info = new ImformacionDispositivos(context);
    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        try {
            allInfo = signalStrength.toString();
            partInfo = allInfo.split(" ");
            switch (info.getTypeOfNetwork234()) {
                case "2G":
                    asu = signalStrength.getGsmSignalStrength();
                    dbm = esAsu(asu);
                    Log.w("MM", "2G");
                    break;
                case "3G":
                    if (Build.VERSION.RELEASE.equals("7.0")) {
                        medidas = info.getSignalStrength();
                        dbm = Integer.parseInt(medidas[0]);
                        asu = Integer.parseInt(partInfo[1]);
                    } else {
                        dbm = Integer.parseInt(partInfo[14]);//14
                        asu = esDbm(Integer.parseInt(partInfo[14]));//14
                        Log.w("INFON DEB", dbm+""+asu);
                    }
                    Log.w("MM", "3G");
                    break;
                case "4G":
                    if (Build.VERSION.RELEASE.equals("11")){
                        dbm = Integer.parseInt(partInfo[21]);//14
                        asu = esDbm(Integer.parseInt(partInfo[21]));//14
                        Log.w("LOG",dbm+""+asu);
                        return;
                    }
                    dbm = Integer.parseInt(partInfo[9]);
                    asu = (Integer.parseInt(partInfo[2]));//140
                    Log.w("MM", "4G");
                    break;
            }
            if (nombreDeFragment.equals("grafica")) {
                lastXpoint++;
                series.appendData(new DataPoint(lastXpoint, dbm), true, 100);
                Log.w("ACC", "Actualizando grafica" + dbm + asu);
            }

            Log.w("MM", "dbm " + dbm + " asu " + asu);
            ponerMedidaSpeed(dbm, asu);
            if (pruebas.btnIniciarPrueba.getText().toString().equalsIgnoreCase("DETENER")) {
                adminSql.insertar(dbm, asu, info);
                onNetworkChange.saveNetworkInfoToDatabase(dbm,asu,info);
            }
        } catch (Exception e) {
            Log.w("MENSAJE", e.getMessage());
        }
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
        String mensajeNotificacion = "";
        if (nombreDeFragment.equals("pruebas")) {
            switch (info.getTypeOfNetwork234()) {
                case "2G":
                    mensajeNotificacion = "El tipo de red es 2G";
                    break;
                case "3G":
                    mensajeNotificacion = "El tipo de red es 3G";
                    break;
                case "4G":
                    mensajeNotificacion = "El tipo de red es 4G";
                    break;
            }
            if (!mensajeNotificacion.equals(""))
                notificacionHelpe.createNotification(mensajeNotificacion, tituloMensajeNotificacion);
        } else
            actualizarGriedView();
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        switch (direction) {
            case TelephonyManager.DATA_CONNECTED:
            case TelephonyManager.DATA_DISCONNECTED:
            case TelephonyManager.DATA_CONNECTING:
                actualizarGriedView();
                break;
        }
    }


    private void actualizarGriedView() {
        recycler_view_data.removeAllViews();
        recycler_view_data.setAdapter(new AdaptadorImformationDevice(info.getImformationRedesMoviles()));
        Log.w("RRD", "Actualizando");
    }

    /**
     * @return int
     * metodo el cual recibe como parametro un int el cual es el asu y
     * regresa un dbm
     */
    private int esAsu(int asu) {
        return ((2 * asu) - 113);
    }

    private int esDbm(int dbm) {
        return (dbm + 120);
    }

    private void ponerMedidaSpeed(int pasu, int pdbm) {
        if (permitirGirar) {
            speedometer.speedTo(pasu);
            speedDeluxe.speedTo(pdbm);
        }
    }

}
