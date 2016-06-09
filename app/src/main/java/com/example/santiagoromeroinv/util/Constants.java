package com.example.santiagoromeroinv.util;

import android.app.Activity;

import com.example.santiagoromeroinv.database.PalabrasDbManager;

/**
 * Created by santiago.romero on 9/06/16.
 */
public class Constants {

    private static PalabrasDbManager palabrasDbManager;

    public static void createWordsList() {

        DibujandoApp dibujandoApp = DibujandoApp.getInstance();
        Activity activity = dibujandoApp.getActivity();

        palabrasDbManager = new PalabrasDbManager(activity);

        palabrasDbManager.insertPalabra("Botella");
        palabrasDbManager.insertPalabra("Persona");
        palabrasDbManager.insertPalabra("Chocolate");
        palabrasDbManager.insertPalabra("Radio");
        palabrasDbManager.insertPalabra("Rey");
        palabrasDbManager.insertPalabra("Mapa");
        palabrasDbManager.insertPalabra("Café");
        palabrasDbManager.insertPalabra("Plato");
        palabrasDbManager.insertPalabra("Parlante");
        palabrasDbManager.insertPalabra("Perro");
        palabrasDbManager.insertPalabra("Zapato");
        palabrasDbManager.insertPalabra("Montaña");
        palabrasDbManager.insertPalabra("Teléfono");
        palabrasDbManager.insertPalabra("Árbol");
        palabrasDbManager.insertPalabra("Rama");
        palabrasDbManager.insertPalabra("Libro");
        palabrasDbManager.insertPalabra("Pistola");
        palabrasDbManager.insertPalabra("Cuchillo");
        palabrasDbManager.insertPalabra("Capucha");
        palabrasDbManager.insertPalabra("Aro");
        palabrasDbManager.insertPalabra("CD");
        palabrasDbManager.insertPalabra("Tomate");
        palabrasDbManager.insertPalabra("Automovil");
        palabrasDbManager.insertPalabra("Camisa");
        palabrasDbManager.insertPalabra("Celular");
        palabrasDbManager.insertPalabra("Átomo");
        palabrasDbManager.insertPalabra("Planta");
        palabrasDbManager.insertPalabra("Silla");
        palabrasDbManager.insertPalabra("Gato");
        palabrasDbManager.insertPalabra("Nota");
        palabrasDbManager.insertPalabra("Estatua");
        palabrasDbManager.insertPalabra("Ángel");
        palabrasDbManager.insertPalabra("Lágrima");
        palabrasDbManager.insertPalabra("Caja");
        palabrasDbManager.insertPalabra("Mar");
        palabrasDbManager.insertPalabra("Vaso");
        palabrasDbManager.insertPalabra("Bomba");
        palabrasDbManager.insertPalabra("Gas");
        palabrasDbManager.insertPalabra("Bolillo");
        palabrasDbManager.insertPalabra("Guante");
        palabrasDbManager.insertPalabra("Casco");
        palabrasDbManager.insertPalabra("Anillo");
        palabrasDbManager.insertPalabra("Nube");
        palabrasDbManager.insertPalabra("Sol");
        palabrasDbManager.insertPalabra("Luna");
        palabrasDbManager.insertPalabra("Satelite");
        palabrasDbManager.insertPalabra("Estrella");
        palabrasDbManager.insertPalabra("Cubo");
        palabrasDbManager.insertPalabra("Homoplato");
        palabrasDbManager.insertPalabra("Dios");

    }
}
