package com.example.santiagoromeroinv.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santiagoromeroinv.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersInfoFragment extends Fragment {


    public DevelopersInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers_info, container, false);
    }

}
