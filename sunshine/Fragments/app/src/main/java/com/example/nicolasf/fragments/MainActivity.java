package com.example.nicolasf.fragments;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nicolasf.fragments.Fragments.DataFragment;
import com.example.nicolasf.fragments.Fragments.DetailFragment;

public class MainActivity extends FragmentActivity implements DataFragment.DataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void sendData(String text) {
        //LLamar el metodo renderizado de el DetailFragment
        //pasando el texto que recibimos por parametro en este mismo metodo

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        detailFragment.renderText(text);

    }

}
