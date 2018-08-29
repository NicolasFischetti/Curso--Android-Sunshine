package com.example.nicolasf.fragments.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nicolasf.fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private TextView details;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        details = (TextView) view.findViewById(R.id.textViewDetails);


        // Inflate the layout for this fragment
        return view;
    }

    public void renderText(String text) {
        details.setText(text);
    }

}
