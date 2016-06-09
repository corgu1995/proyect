package com.example.santiagoromeroinv.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by santiago.parrar on 4/05/16.
 */
public class BluetoothDeviceArrayAdapter extends ArrayAdapter {
    private List<BluetoothDevice> deviceList;
    private Context context;
    public BluetoothDeviceArrayAdapter(Context context, int textViewResourceId,
                                       List<BluetoothDevice> objects) {
        super(context, textViewResourceId, objects);
        this.deviceList = objects;
        this.context = context;
    }



@Override
public int getCount()
        {
        if(deviceList != null)
        return deviceList.size();
        else
        return 0;
        }


@Override
public Object getItem(int position)
        {
        return (deviceList == null ? null : deviceList.get(position));
        }

@Override
public View getView(int position, View convertView, ViewGroup parent)
        {
        if((deviceList == null) || (context == null))
        return null;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View elemento = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        TextView txtNombre = (TextView)elemento.findViewById(android.R.id.text1);
        TextView txtDireccion = (TextView)elemento.findViewById(android.R.id.text2);
        BluetoothDevice dispositivo = (BluetoothDevice)getItem(position);
//Como se puede ver se utiliza un simple_list_item_2, este se utiliza ya que posee dos TextView y en este caso nos interesa mostrar el nombre y la dirección del dispositivo.
        if(dispositivo != null)
        {
        txtNombre.setText(dispositivo.getName());
        txtDireccion.setText(dispositivo.getAddress());
        }
        else
        {
        txtNombre.setText("ERROR");
        }
        return elemento;
        }


        }
