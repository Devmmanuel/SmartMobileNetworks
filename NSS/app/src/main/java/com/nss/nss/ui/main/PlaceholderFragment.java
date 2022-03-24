package com.nss.nss.ui.main;
import androidx.fragment.app.Fragment;

import com.nss.nss.ui.graph.GraphFragment;
import com.nss.nss.ui.historicos.Historical;
import com.nss.nss.ui.imformacion_device.ImformationMobile;
import com.nss.nss.ui.pruebas.pruebas;


public class PlaceholderFragment extends Fragment {


    public static Fragment newInstance(int index) {
        Fragment fragment = null;
        switch (index) {
            case 1:
                fragment = new ImformationMobile();
                break;
            case 2:
                fragment = new GraphFragment();
                break;
            case 3:
                fragment = new Historical();
                break;
            case 4:
                fragment = new pruebas();
                break;
        }
        return fragment;
    }

}