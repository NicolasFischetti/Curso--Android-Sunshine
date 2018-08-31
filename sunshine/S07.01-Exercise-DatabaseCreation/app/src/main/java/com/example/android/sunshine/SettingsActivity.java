/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**

 * La setting activity es la responsable de mostrar el Setting Fragment.
 * Cuando apretamos el up boton nos manda a la activity de la cual vino el usuario para ingresar a las setting activitys.
 * <p>
 * Si el usuario entra a las setting desde la detail activity cuando aprete el boton up vuelve a las detailActivity. lo mismo
 * para el caso del main activity
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
           *Normalmente, llamando a setDisplayHomeAsUpEnabled (true) (lo hacemos en onCreate aquí), así como
         * declarar la actividad principal en el AndroidManifest es todo lo que se requiere para obtener el
         * botón arriba funcionando correctamente.
         *  Se usa home id para acceder al botton up y luego el OnbackPressed para volver a la pantalla anterior
         */
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}