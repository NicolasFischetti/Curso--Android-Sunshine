package com.example.ficha.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    private Button btn;
    private final String GLEETER = "Hola from the other side";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // meter un icono en la action bar(forzar)
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);

        btn = (Button) findViewById(R.id.buttonMain);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ACCEDER AL SEGUNDO ACTIVITY Y MANDAR STRING
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("greeter", GLEETER);
                startActivity(intent);
            }
        });
    }

}
