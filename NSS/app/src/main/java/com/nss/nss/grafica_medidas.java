package com.nss.nss;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



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
    private DbmAsu gra;


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
        gra = new DbmAsu();
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
                new DataPoint(0,1),
                new DataPoint(1,3),
                new DataPoint(2,2),
        });
        graph.addSeries(series);

        viewport.setBackgroundColor(Color.parseColor("#1D2D6D"));
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

    private void addRandomDataPoint(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastXpoint++;
                series.appendData(new DataPoint(lastXpoint,-60),true,100);
                addRandomDataPoint();
                Log.w("MENSAJE",String.valueOf(gra.getDbm()));
            }
        },1000);
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
