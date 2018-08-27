package com.example.ficha.myapplication;

public class Tren{
    int vagones [] [] = new int [5] [2];
    String maquinista;
    int antiguedad;

    public Tren() {
        for(int i = 0; i <=4; i++) {
            vagones [i] [0] = (i+1) * 10;
            vagones [i] [1] = 0;

        }
        maquinista = "Anonimo";
        antiguedad = 0;
    }
}
