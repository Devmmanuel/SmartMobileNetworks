package com.nss.nss;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link historicos_pruebas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link historicos_pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class historicos_pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TableLayout table;
    private AdminSql sql;
    private SQLiteDatabase db;
    private ArrayList<String> registros;
    private int num_Rows;
    private Button btnBuscar;
    private EditText txtBuscar;
    private boolean buscando = false;


    private OnFragmentInteractionListener mListener;

    public historicos_pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historicos_pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static historicos_pruebas newInstance(String param1, String param2) {
        historicos_pruebas fragment = new historicos_pruebas();
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
        View vista = inflater.inflate(R.layout.fragment_historicos_pruebas, container, false);
        table = vista.findViewById(R.id.tablelayout);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        txtBuscar = vista.findViewById(R.id.txtBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Botton presionado", Toast.LENGTH_SHORT).show();
                table.removeAllViews();
                buscando = true;
                agregarRegistrosAtabla();
                Log.w("M",regresarRegistros().toString());
            }
        });
        agregarRegistrosAtabla();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void agregarRegistrosAtabla(){
         if(buscando==true){
             regresarRegistrosConsulta(null,txtBuscar.getText().toString());
             buscando = false;
         }else
             regresarRegistros();
        int contador = 0;
        for (int i = 0; i < num_Rows; i++) {
            TableRow tableRow = new TableRow(getActivity());
            for (int j = 0; j < 7; j++) {
                TextView textView = new TextView(getActivity());
                textView.setPadding(11, 11, 11, 11);
                textView.setBackgroundResource(R.color.colorNnsAuxiliar);
                textView.setTypeface(Typeface.MONOSPACE);
                textView.setTextColor(getResources().getColor(R.color.colorNnsAuxiliar2));
                textView.setText(registros.get(contador));
                tableRow.addView(textView);
                contador++;
            }
            table.addView(tableRow);
        }
    }

    /*** metodo el cual regresa un arrayLIst con todos los elementos de la base de datos
     * @return ArrayList<String>
     */
    public ArrayList<String> regresarRegistros() {
        try {
            registros = new ArrayList<>();
            sql = new AdminSql(getActivity(), "mydb", null, 1);
            db = sql.getWritableDatabase();
            Cursor fila = db.rawQuery("select * from historicosRedesMoviles", null);
            num_Rows = fila.getCount();
            if (fila.moveToFirst()) {
                do {
                    registros.add(fila.getString(0));
                    registros.add(fila.getString(1));
                    registros.add(fila.getString(2));
                    registros.add(fila.getString(3));
                    registros.add(fila.getString(4));
                    registros.add(fila.getString(5));
                    registros.add(fila.getString(6));
                } while (fila.moveToNext());
                Log.w("MENSAJE", registros.toString());
                fila.close();
            }
            //*****else Toast.makeText(getActivity(), "No hay ningun registro en la base de datos", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            ///Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return registros;
    }

    public ArrayList<String> regresarRegistrosConsulta(View v, String pbuscador) {
        try {
            String consulta;
            if(pbuscador.equals("")){
            consulta = "select * from historicosRedesMoviles";
            }else
            consulta="select * from historicosRedesMoviles where fecha='"+pbuscador+"'";
            registros = new ArrayList<>();
            sql = new AdminSql(getActivity(), "mydb", null, 1);
            db = sql.getWritableDatabase();
            Cursor fila = db.rawQuery(consulta, null);
            num_Rows = fila.getCount();
            if (fila.moveToFirst()) {
                do {
                    registros.add(fila.getString(0));
                    registros.add(fila.getString(1));
                    registros.add(fila.getString(2));
                    registros.add(fila.getString(3));
                    registros.add(fila.getString(4));
                    registros.add(fila.getString(5));
                    registros.add(fila.getString(6));
                } while (fila.moveToNext());
                Log.w("MENSAJE", registros.toString());
                fila.close();
            }
            ///***else Toast.makeText(getActivity(), "No hay ningun registro en la base de datos ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return registros;
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
