package com.example.santiagoromeroinv.util;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.example.santiagoromeroinv.bluetooth.BluetoothService;
import com.example.santiagoromeroinv.database.PalabrasDbManager;
import com.example.santiagoromeroinv.drawing.DrawingView;


/**
 * Created by santiago.romero on 9/06/16.
 */
public class Constants {
    private static int drawSize;
    private static PalabrasDbManager palabrasDbManager;
    private static BluetoothService service;
    private static BluetoothDevice device;
    private static boolean slave=true;
    private static String word="";
    private static DrawingView drawView;

    public static DrawingView getDrawView() {
        return drawView;
    }

    public static void setDrawView(DrawingView drawView) {
        Constants.drawView = drawView;
    }

    public static String getWord() {

        return word;
    }

    public static boolean isSlave() {
        return slave;
    }

    public static void setSlave(boolean slave) {
        Constants.slave = slave;
    }

    public static int getDrawSize() {
        return drawSize;
    }

    public static void setDrawSize(int drawSize) {
        Constants.drawSize = drawSize;
    }
    public static BluetoothDevice getDevice() {
        return device;
    }

    public static void setDevice(BluetoothDevice device) {
        Constants.device = device;
    }
    public static BluetoothService getService() {
        return service;
    }

    public static void setService(BluetoothService service) {
        Constants.service = service;
    }


    public static void createWordsList() {

        DibujandoApp dibujandoApp = DibujandoApp.getInstance();
        Activity activity = dibujandoApp.getActivity();

        palabrasDbManager = new PalabrasDbManager(activity);

        if (palabrasDbManager.getCount()==0) {
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
        word = palabrasDbManager.getAllWords().get(10).toString();
    }
}
