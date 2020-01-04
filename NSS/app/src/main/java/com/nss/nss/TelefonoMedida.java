package com.nss.nss;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;

import java.util.List;

public class TelefonoMedida extends PhoneStateListener {

    private String allInfo;
    private String[] partInfo;
    private int asu;
    private int dbm;
    private imformacionDispositivos info;
    private AdminSql adminSql;
    private String[] medidas = new String[2];
    private boolean permitirGirar;
    private NotificationHelpener notificacionHelpe;
    private String tituloMensajeNotificacion = "La red cambio";
    private String nombreDeFragment;
    /*variables usadas con instanciaos desde el fragmente de pruebas*/
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    /*esta variable nos servida para saber en que fragment estamos ubicado y que metodos debemos de ejecutar dependiendo de que
     * fragment esramos ubicado*/

    private ArrayAdapter adaptadorDatosRedes;
    private List<String> listDatosRm;

    public void setPermitirGirar(boolean permitirGirar) {
        this.permitirGirar = permitirGirar;
    }


    /*constrcutor que es usado cuando estamos instanciado la clase desde el fragmente de pruebas*/
    public TelefonoMedida(Context context, SpeedView sp, DeluxeSpeedView dx) {
        nombreDeFragment = "pruebas";
        speedDeluxe = dx;
        speedometer = sp;
        notificacionHelpe = new NotificationHelpener(context);
        info = new imformacionDispositivos(context);
        adminSql = new AdminSql(context, "mydb", null, 1);
    }
    /*Contructor el cual usamos cuando estamos instanciando la clase desde el fragmente de imformacion*/
    public TelefonoMedida(ArrayAdapter datosRedes, Context context, List<String> datosRM) {
        nombreDeFragment = "imformacion";
        adaptadorDatosRedes = datosRedes;
        listDatosRm = datosRM;
        info = new imformacionDispositivos(context);

    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        try {
            allInfo = signalStrength.toString();
            partInfo = allInfo.split(" ");
            if (info.getTypeOfNetwork234().equals("2G")) {
                asu = signalStrength.getGsmSignalStrength();
                dbm = esAsu(asu);
                ponerMedidaSpeed(dbm, asu);
            }
            if (info.getTypeOfNetwork234().equals("3G")) {
                if (Build.VERSION.RELEASE.equals("7.0")) {
                    medidas = info.getSignalStrength();
                    dbm = Integer.parseInt(medidas[0]);
                    asu = Integer.parseInt(partInfo[1]);
                } else {
                    dbm = Integer.parseInt(partInfo[14]);//14
                    asu = esDbm(Integer.parseInt(partInfo[14]));//14
                }
                ponerMedidaSpeed(dbm, asu);
            }
            if (info.getTypeOfNetwork234().equals("4G")) {
                dbm = Integer.parseInt(partInfo[9]);
                asu = (Integer.parseInt(partInfo[2]));//140
                ponerMedidaSpeed(dbm, asu);
            }
            if (pruebas.btnIniciarPrueba.getText().toString().equalsIgnoreCase("DETENER")) {
                adminSql.insertar(dbm, asu, info);
            }
            Log.w("MENSAJE", dbm + "----" + asu);
        } catch (Exception e) {
            Log.w("MENSAJE", e.getMessage());
        }
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
        String mensajeNotificacion = "";
        if(nombreDeFragment.equals("pruebas")){
            if (info.getTypeOfNetwork234().equals("2G"))
                mensajeNotificacion = "El tipo de red es 2G";
            if (info.getTypeOfNetwork234().equals("3G"))
                mensajeNotificacion = "El tipo de red es 3G";
            if (info.getTypeOfNetwork234().equals("4G"))
                mensajeNotificacion = "El tipo de red es 4G";
            if (!mensajeNotificacion.equals(""))
                notificacionHelpe.createNotification(mensajeNotificacion, tituloMensajeNotificacion);
        }
        else
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


    public void actualizarGriedView() {
        adaptadorDatosRedes.clear();
        info.getImformationRedesMoviles(listDatosRm);
        Log.w("RRD", "Actualizando");
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

    public void ponerMedidaSpeed(int pasu, int pdbm) {
        if (permitirGirar) {
            speedometer.speedTo(pasu);
            speedDeluxe.speedTo(pdbm);
        }
    }

}
