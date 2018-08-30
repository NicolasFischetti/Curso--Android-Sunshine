package com.example.nicolasf.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> names;

    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mAdapter; // se puede usar asi porque esta extendiendo la clase
    private  RecyclerView.LayoutManager nLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = this.getAllNames();

        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        nLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(names, R.layout.recycler_view_item, new MyAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(MainActivity.this, name + " - " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private  List<String> getAllNames() {
        return new ArrayList<String>() {{
            add("Nico");
            add("Nico2");
            add("Nico3");
            add("Nico3");
            add("Nico3");
            add("Nico3");
        }};
    }
}
