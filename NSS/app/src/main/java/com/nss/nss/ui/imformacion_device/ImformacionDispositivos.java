package com.nss.nss.ui.imformacion_device;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ImformacionDispositivos {

    private TelephonyManager tm;
    private Context ctx;


    public ImformacionDispositivos(Context ctx) {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        this.ctx = ctx;
    }

    public String getStateRoaming(TelephonyManager tm) {
        if (tm.isNetworkRoaming())
            return "Activado";
        else
            return "Desactivado";
    }


    public String getCodigoPais() {
        return tm.getSimCountryIso();
    }


    public String[] getMccAndMnc() {
        String infoMccYmnc = tm.getNetworkOperator();
        String[] mccyMnc = new String[2];
        mccyMnc[0] = infoMccYmnc.substring(0, 3);
        mccyMnc[1] = infoMccYmnc.substring(3, 6);
        return mccyMnc;
    }

    public List<String> getImformationRedesMoviles() {
        List<String> datosRM = new ArrayList<>();
        datosRM.add("Operador");
        datosRM.add(getOperator());
        datosRM.add("Tipo de red telefonica");
        datosRM.add(getTypeOfNetwork());
        datosRM.add("Tipo de red");
        datosRM.add(getTypeOfNetwork234());
        datosRM.add("Codigo de pais");
        datosRM.add(getCodigoPais());
        datosRM.add("mcc");
        datosRM.add(getMccAndMnc()[0]);
        datosRM.add("mnc");
        datosRM.add(getMccAndMnc()[1]);
        datosRM.add("Roamig");
        datosRM.add(getStateRoaming(tm));
        datosRM.add("Phone type");
        datosRM.add(getPhoneType());
        datosRM.add("Data conected");
        datosRM.add(getDataConected());
        datosRM.add("Imei");
        datosRM.add("Null");
        datosRM.add("ip");
        datosRM.add(getMobileIPAddress());
        datosRM.add("Mac");
        datosRM.add(getMacAddress());
        return datosRM;
    }

    public String getOperator() {
        return tm.getNetworkOperatorName();
    }


    public String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(":"));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.w("ERROR", ex.getMessage());
        }
        return "";
    }

    public String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.w("ERROR", ex.getMessage());
        } // for now eat exceptions
        return "-";
    }


    public String getnImei() {
        int chekarPermiso = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE);
        if (chekarPermiso == PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= 26)
                return tm.getImei();
            else return tm.getDeviceId();
        else
            return "Desconocido";
    }

    public String getPhoneType() {
        String phoneType = "Unknown";
        switch (tm.getPhoneType()) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneType = " CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneType = " GMS";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneType = " NONE";
                break;
            case (TelephonyManager.PHONE_TYPE_SIP):
                phoneType = " SIP";
                break;
        }
        return phoneType;
    }

    public String getDataConected() {
        String dataConected = "";
        switch (tm.getDataState()) {
            case TelephonyManager.DATA_DISCONNECTED:
                dataConected = "Desconectado";
                break;
            case TelephonyManager.DATA_CONNECTED:
                dataConected = "Conectado";
                break;
            case TelephonyManager.DATA_CONNECTING:
                dataConected = "Conectando";

        }
        return dataConected;
    }

    public String getTypeOfNetwork() {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
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
                return "Unk";
        }
        throw new RuntimeException("New type of network");

    }

    public String getTypeOfNetwork234() {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
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
                return "4G";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unk";
        }
        throw new RuntimeException("New type of network");
    }

    /**
     * en android 8.1 es necesario tener encendidad la localizacion para acceder a estos metodos
     *
     * @return String
     * @throws SecurityException Este metodo regresa un string con el actual dbm que se esta recibiendo en el dispositivo
     */
    public String[] getSignalStrength() throws SecurityException {
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String[] strength = new String[2];
        List<CellInfo> cellInfos = Objects.requireNonNull(telephonyManager).getAllCellInfo();
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
        }
        return strength;
    }


}
