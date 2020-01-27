package com.nss.nss;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link grafica_medidas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link grafica_medidas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class grafica_medidas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Handler handler = new Handler();
    private LineGraphSeries<DataPoint> series;
    private double lastXpoint = 1;
    private String mensaje="Debes de de activar la ubicacion en android 8.0 para mostrar la grafica en tiempo real";



    private OnFragmentInteractionListener mListener;

    public grafica_medidas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment grafica_medidas.
     */
    // TODO: Rename and change types and number of parameters
    public static grafica_medidas newInstance(String param1, String param2) {
        grafica_medidas fragment = new grafica_medidas();
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
        View view = inflater.inflate(R.layout.fragment_grafica_medidas, container, false);

        GraphView graph = view.findViewById(R.id.graph);
        Viewport viewport = graph.getViewport();
        GridLabelRenderer gridlabel = graph.getGridLabelRenderer();

        graph.setTitle("Grafica en tiempo real dBm");
        graph.setTitleTextSize(15);
        graph.setTitleColor(Color.RED);
        gridlabel.setPadding(10);

        gridlabel.setGridColor(Color.BLACK);

        gridlabel.setHorizontalAxisTitleTextSize(15);
        gridlabel.setHorizontalAxisTitle("Segundos");
        gridlabel.setHorizontalAxisTitleColor(Color.RED);

        gridlabel.setVerticalAxisTitleTextSize(15);
        gridlabel.setVerticalAxisTitle("dBm");
        gridlabel.setVerticalAxisTitleColor(Color.RED);

        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 2),
        });

        series.setColor(Color.GREEN);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(50, 0, 255, 0));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        series.setThickness(3);

        graph.addSeries(series);

        viewport.setBackgroundColor(Color.BLACK);
        viewport.setMinX(0);
        viewport.setMaxX(10);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinY(-120);
        viewport.setMaxY(-50);
        viewport.setYAxisBoundsManual(true);
        viewport.setScalable(true);
        addRandomDataPoint();


        return view;
    }


    /*este metodo obtiene el dbm Level en  en diferentes tipos de redes**/
    private String getSignalStrength(Context context) throws SecurityException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String strength = "0";
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
        if (cellInfos != null) {
            for (int i = 0; i < cellInfos.size(); i++) {
                if (cellInfos.get(i).isRegistered()) {
                    if (cellInfos.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfos.get(i);
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthWcdma.getDbm() - 31);
                    } else if (cellInfos.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm cellInfogsm = (CellInfoGsm) cellInfos.get(i);
                        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthGsm.getDbm() - 31);
                    } else if (cellInfos.get(i) instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfos.get(i);
                        CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthLte.getDbm() - 3);
                    } else if (cellInfos.get(i) instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfos.get(i);
                        CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthCdma.getDbm() - 31);
                    }
                }
            }
        }
        if (strength.equals("0")){
            Toast.makeText(context, mensaje,Toast.LENGTH_SHORT).show();
            handler.removeCallbacksAndMessages(null);
        }

        return strength;
    }


    private void addRandomDataPoint() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastXpoint++;
                series.appendData(new DataPoint(lastXpoint, Integer.parseInt(getSignalStrength(getActivity()))), true, 100);
                addRandomDataPoint();
                Log.w("MENSAJE", getSignalStrength(getActivity()));
            }
        }, 1000);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
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
