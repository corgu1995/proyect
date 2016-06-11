package com.example.santiagoromeroinv.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagoromeroinv.R;
import com.example.santiagoromeroinv.bluetooth.BluetoothDeviceArrayAdapter;
import com.example.santiagoromeroinv.bluetooth.BluetoothService;
import com.example.santiagoromeroinv.drawing.DrawingActivity;
import com.example.santiagoromeroinv.drawing.DrawingView;
import com.example.santiagoromeroinv.util.Constants;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;

public class SettingsActivity extends Activity implements View.OnClickListener {

    private static final int   REQUEST_ENABLE_BT  = 1;
    private static final String ALERTA  = "alerta";

    private Button btnEnviar;
    private DrawingView drawView;
    private Button btnBluetooth;
    private Button btnBuscarDispositivo;
    private Button btnConectarDispositivo;
    private Button btnSalir;
    private EditText txtMensaje;
    public Bitmap bmp;
    private TextView tvMensaje;
    private TextView tvConexion;
    private ListView lvDispositivos;

    private BluetoothAdapter bAdapter;             // Adapter para uso del Bluetooth
    private ArrayList<BluetoothDevice> arrayDevices;   // Listado de dispositivos
    private ArrayAdapter arrayAdapter;             // Adaptador para el listado de dispositivos

    private static BluetoothService servicio;           // Servicio de mensajes de Bluetooth, esta clase se encarga de manejar los hilos de conexión
    private BluetoothDevice       ultimoDispositivo;    // Último dispositivo conectado

    private final BroadcastReceiver bReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();

            //Código que se ejecutará cuando el Bluetooth cambie su estado.
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
            {
                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);

            switch (estado)
            {
                // Apagado
                case BluetoothAdapter.STATE_OFF:
                {
                    ((Button)findViewById(R.id.btnBluetooth)).setText("Activar Bluetooth");
                    ((Button)findViewById(R.id.btnBuscarDispositivo)).setEnabled(false);
                    ((Button)findViewById(R.id.btnConectarDispositivo)).setEnabled(false);
                    break;
                }

                // Encendido
                case BluetoothAdapter.STATE_ON:
                {
                    ((Button)findViewById(R.id.btnBluetooth)).setText("Desactivar Bluetooth");
                    ((Button)findViewById(R.id.btnBuscarDispositivo)).setEnabled(true);
                    ((Button)findViewById(R.id.btnConectarDispositivo)).setEnabled(true);

                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                    startActivity(discoverableIntent);

                    break;
                }
                default:
                    break;
            } // Fin switch

            } // Fin if

//Ahora se prosigue con la etapa de descubrimiento de dispositivos, cada vez que un nuevo dispositivo sea descubierto se ejecutara esta sección de código
            else if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                if(arrayDevices == null)
                    arrayDevices = new ArrayList<BluetoothDevice>();
                //Se crea la lista de dispositivos
                BluetoothDevice dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayDevices.add(dispositivo);
                String descripcionDispositivo = dispositivo.getName() + " [" + dispositivo.getAddress() + "]";
                Toast.makeText(getBaseContext(), "Dispositivo Detectado" + ": " + descripcionDispositivo, Toast.LENGTH_SHORT).show();
//se crea la lista con el nombre del dispositivo y su dirección
            }

            // Código que se ejecutará cuando el Bluetooth finalice la búsqueda de dispositivos, se debe recalcar que esta búsqueda consume muchos recursos, por lo cual se debe finalizar cada que no se necesite .
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                // Instanciamos un nuevo adapter para el ListView
                arrayAdapter = new BluetoothDeviceArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_2, arrayDevices);
                lvDispositivos.setAdapter(arrayAdapter);
                Toast.makeText(getBaseContext(), "Fin de la usqueda", Toast.LENGTH_SHORT).show();
            }

        } // Fin onReceive
    };

    private void registrarEventosBluetooth()
    {
        // Registramos el BroadcastReceiver que instanciamos previamente para
        // detectar los distintos eventos que queremos recibir
        IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        filtro.addAction(BluetoothDevice.ACTION_FOUND);
        filtro.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        this.registerReceiver(bReceiver, filtro);
    }

    public void conectarDispositivo(String direccion)
    {
        Toast.makeText(this, "Conectando a " + direccion, Toast.LENGTH_LONG).show();
        if(servicio != null)
        {
            BluetoothDevice dispositivoRemoto = bAdapter.getRemoteDevice(direccion);
            servicio.solicitarConexion(dispositivoRemoto);
            this.ultimoDispositivo = dispositivoRemoto;
        }
    }

    public void enviarMensaje(String mensaje)
    {
        if(servicio.getEstado() != BluetoothService.ESTADO_CONECTADO)
        {
            Toast.makeText(this,"Error en la conexion", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mensaje.length() > 0)
        {
            byte[] buffer = mensaje.getBytes();
            servicio.enviar(buffer);
        }
    }

    public void onClick(View v) {
        switch(v.getId())
        {

            // Código ejecutado al pulsar el Button que se va a encargar de enviar los
            // datos al otro dispositivo.
            case R.id.btnEnviar:
            {
                if(servicio != null)
                {
                    servicio.enviar(txtMensaje.getText().toString().getBytes());
                    txtMensaje.setText("");
                }
                break;
            }

            // Código ejecutado al pulsar el Button que se va a encargar de activar y
            // desactivar el Bluetooth.
            case R.id.btnBluetooth:
            {
                if(bAdapter.isEnabled())
                {
                    if(servicio != null)
                        servicio.finalizarServicio();

                    bAdapter.disable();
                }
                else
                {
                    // Lanzamos el Intent que mostrara la interfaz de activación del Bluetooth.
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                break;
            }

            // Código ejecutado al pulsar el Button que se va a encargar de descubrir nuevos
            //dispositivos
            case R.id.btnBuscarDispositivo:
            {
                arrayDevices.clear();

                // Comprobamos si existe un descubrimiento en curso. En caso afirmativo, se cancela.
                if(bAdapter.isDiscovering())
                    bAdapter.cancelDiscovery();

                // Iniciamos la búsqueda de dispositivos
                if(bAdapter.startDiscovery())
                    // Mostramos el mensaje de que el proceso ha comenzado
                    Toast.makeText(this, "Iniciando descubrimiento", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Error iniciando descubrimiento", Toast.LENGTH_SHORT).show();
                break;
            }

            // Código que se ejecutará  al pulsar el Button que se encarga de mostrar todos los dispositivos previamente enlazados al dispositivo actual.
            case R.id.btnConectarDispositivo:
            {
                Set<BluetoothDevice> dispositivosEnlazados = bAdapter.getBondedDevices();
                // Instanciamos un nuevo adapter para el ListView
                arrayDevices = new ArrayList<BluetoothDevice>(dispositivosEnlazados);
                arrayAdapter = new BluetoothDeviceArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, arrayDevices);
                lvDispositivos.setAdapter(arrayAdapter);
                Toast.makeText(getBaseContext(), "Fin de la busqueda", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnSalir:
            {
                Intent i = new Intent(this,DrawingActivity.class);
                startActivityForResult(i, 15);
                break;
            }
            default:
                break;
        }
    }

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            byte[] buffer  = null;
            byte[] bytes  = null;
            String mensaje     = null;

            //Se verifica el estado nuevamente con una estructura switch
            switch(msg.what)
            {
                // Mensaje de lectura: En este caso se convierte el mensaje para ser mostrado
                case BluetoothService.MSG_LEER:
                {
                    buffer = (byte[])msg.obj;
                    mensaje = new String(buffer, 0, msg.arg1);
                    tvMensaje.setText(mensaje);
                    try{
                        //DrawingActivity.setWordsText(mensaje);
                        bytes = (byte[])msg.obj;
                        bmp=drawView.getDrawingCache();
                        Bitmap.Config configBmp = Bitmap.Config.valueOf(bmp.getConfig().name());
                        int width = bmp.getWidth();
                        int height = bmp.getHeight();
                        Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, configBmp);
                        ByteBuffer buffer1 = ByteBuffer.wrap(bytes);
                        bitmap_tmp.copyPixelsFromBuffer(buffer1);
                        drawView.bitM(bitmap_tmp);

                    }catch (Exception e){}
                    break;
                }

                // Mensaje de escritura:
                case BluetoothService.MSG_ESCRIBIR:
                {
                    buffer = (byte[])msg.obj;
                    mensaje = new String(buffer);
                    mensaje = "Enviando mensaje" + ": " + mensaje;
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    break;
                }

                // Mensaje de cambio de estado
                case BluetoothService.MSG_CAMBIO_ESTADO:
                {
                    switch(msg.arg1)
                    {
                        case BluetoothService.ESTADO_ATENDIENDO_PETICIONES:
                            break;

                        // CONECTADO: Se muestra el dispositivo al que se ha conectado y se activa el botón de enviar
                        case BluetoothService.ESTADO_CONECTADO:
                        {
                            mensaje = "Conexion actual:" + " " + servicio.getNombreDispositivo();
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                            tvConexion.setText(mensaje);
                            btnEnviar.setEnabled(true);
                            break;
                        }

                        // REALIZANDO CONEXIÓN: Se muestra el dispositivo al que se está conectando
                        case BluetoothService.ESTADO_REALIZANDO_CONEXION:
                        {
                            mensaje = "Conectando a:" + " " + ultimoDispositivo.getName() + " [" + ultimoDispositivo.getAddress() + "]";
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                            btnEnviar.setEnabled(false);
                            break;
                        }

                        // NINGUNO: Mensaje por defecto. Desactivación del botón de enviar
                        case BluetoothService.ESTADO_NINGUNO:
                        {
                            mensaje = "Sin conexion";
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                            tvConexion.setText(mensaje);
                            btnEnviar.setEnabled(false);
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                }

                // Mensaje de alerta: se mostrará en el Toast
                case BluetoothService.MSG_ALERTA:
                {
                    mensaje = msg.getData().getString(ALERTA);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    break;
                }

                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        configurarControles();
        Constants.setService(servicio);
    }


    private void configurarControles()
    {
        // Instanciamos el array de dispositivos
        arrayDevices = new ArrayList<BluetoothDevice>();

        // Referenciamos los controles y registramos los listeners
        referenciarControles();
        registrarEventosControles();

        // Se desactivan los botones que no se pueden utilizar
        btnEnviar.setEnabled(false);
        btnBuscarDispositivo.setEnabled(false);
        btnConectarDispositivo.setEnabled(false);

        // Configuramos el adaptador bluetooth y nos suscribimos a sus eventos
        configurarAdaptadorBluetooth();
        registrarEventosBluetooth();
    }
    /**
     * Referencia los elementos de interfaz
     */
    private void referenciarControles()
    {
        // Referenciamos los elementos de interfaz
        btnEnviar = (Button)findViewById(R.id.btnEnviar);
        btnBluetooth = (Button)findViewById(R.id.btnBluetooth);
        btnBuscarDispositivo = (Button)findViewById(R.id.btnBuscarDispositivo);
        btnConectarDispositivo = (Button)findViewById(R.id.btnConectarDispositivo);
        btnSalir = (Button)findViewById(R.id.btnSalir);
        txtMensaje = (EditText)findViewById(R.id.txtMensaje);
        tvMensaje = (TextView)findViewById(R.id.tvMensaje);
        tvConexion = (TextView)findViewById(R.id.tvConexion);
        lvDispositivos = (ListView)findViewById(R.id.lvDispositivos);
    }

    /**
     * Registra los eventos de interfaz (eventos onClick, onItemClick, etc.)
     */
    private void registrarEventosControles()
    {
        // Asignamos los handlers de los botones
        btnEnviar.setOnClickListener(this);
        btnBluetooth.setOnClickListener(this);
        btnBuscarDispositivo.setOnClickListener(this);
        btnConectarDispositivo.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        // Configuramos la lista de dispositivos para que cuando seleccionemos
        // uno de sus elementos realice la conexión al dispositivo, para poder enviar y recibir mensajes
        configurarListaDispositivos();
    }



    /**
     * Se configura el ListView para que responda a los eventos de pulsación
     */
    private void configurarListaDispositivos()
    {
        lvDispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapter, View view, int position, long arg) {
                // El ListView tiene un adaptador de tipo BluetoothDeviceArrayAdapter, esta clase es la encargada de listar los dispositivos.
                // Invocamos el método getItem() del adaptador para recibir el dispositivo
                // bluetooth y realizar la conexión.
                BluetoothDevice dispositivo = (BluetoothDevice) lvDispositivos.getAdapter().getItem(position);

                AlertDialog dialog = crearDialogoConexion("Conectar",
                        "Confirmar conexion" + " " + dispositivo.getName() + "?",
                        dispositivo.getAddress());

                dialog.show();
            }
        });
    }

    private AlertDialog crearDialogoConexion(String titulo, String mensaje, final String direccion)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos título y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensaje);

        // Creamos un nuevo OnClickListener para el botón OK que realice la conexión
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                conectarDispositivo(direccion);
            }
        };

        // Creamos un nuevo OnClickListener para el botón Cancelar
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };

        // Asignamos los botones positivo y negativo a sus respectivos listeners
        alertDialogBuilder.setPositiveButton("Conectar", listenerOk);
        alertDialogBuilder.setNegativeButton("Cancelar", listenerCancelar);

        return alertDialogBuilder.create();
    }

    /**
     * Configura el BluetoothAdapter y los botones asociados
     */
    private void configurarAdaptadorBluetooth()
    {
        // Obtenemos el adaptador Bluetooth. Si es NULL, significa que el
        // dispositivo no posee Bluetooth, por lo que deshabilitamos el botón
        // encargado de activar/desactivarlo.
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bAdapter == null)
        {
            btnBluetooth.setEnabled(false);
            return;
        }

        // Comprobamos si el Bluetooth está activo y cambiamos el texto de los botones
        // dependiendo del estado. También activamos o desactivamos los botones
        // asociados a la conexión
        if(bAdapter.isEnabled())
        {
            btnBluetooth.setText("Desactivar bluetooth");
            btnBuscarDispositivo.setEnabled(true);
            btnConectarDispositivo.setEnabled(true);
        }
        else
        {
            btnBluetooth.setText("Activar bluetooth");
        }
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch(requestCode)
        {
            case REQUEST_ENABLE_BT:
            {
                if(resultCode == RESULT_OK)
                {
                    btnBluetooth.setText("Desactivar bluetooth");
                    if(servicio != null)
                    {
                        servicio.finalizarServicio();
                        servicio.iniciarServicio();
                    }
                    else
                        servicio = new BluetoothService(this, handler, bAdapter);
                }
                break;
            }
            default:
                break;
        }
    }

    // Además de realizar la destrucción de la actividad, eliminamos el registro del
    // BroadcastReceiver, se vuelve a hacer énfasis en que esta parte es muy importante ya que el proceso consume muchos recursos.
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(bReceiver);
        if(servicio != null)
            servicio.finalizarServicio();
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(servicio != null)
        {
            if(servicio.getEstado() == BluetoothService.ESTADO_NINGUNO)
            {
                servicio.iniciarServicio();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    public  void sendMessage(String msg){
        Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public static BluetoothService getService() {
        return servicio;
    }
}




