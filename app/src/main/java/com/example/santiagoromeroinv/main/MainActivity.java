package com.example.santiagoromeroinv.main;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.santiagoromeroinv.activities.SettingsActivity;
import com.example.santiagoromeroinv.database.LoginDbManager;
import com.example.santiagoromeroinv.R;

import com.example.santiagoromeroinv.database.PalabrasDbManager;
import com.example.santiagoromeroinv.drawing.DrawingActivity;
import com.example.santiagoromeroinv.layout.ActivityMainFragment;
import com.example.santiagoromeroinv.layout.AppInfoFragment;
import com.example.santiagoromeroinv.layout.DevelopersInfoFragment;
import com.example.santiagoromeroinv.login.LoginActivity;
import com.example.santiagoromeroinv.util.Constants;
import com.example.santiagoromeroinv.util.DibujandoApp;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int   REQUEST_ENABLE_BT  = 1;
    private String[] opciones = null;
    private DrawerLayout drawerLayout = null;
    private ListView listView = null;
    private CharSequence tituloSec = null;
    private Button letsPlayButton = null;
    private static final int REQUEST_CODE=11;
    private ActionBarDrawerToggle drawerToggle = null;
    private DibujandoApp dibujandoApp = null;
    private ActivityMainFragment mainFragment = null;
    private LoginDbManager loginDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dibujandoApp = DibujandoApp.getInstance();
        dibujandoApp.setActivity(this);

        Constants.createWordsList();

        letsPlayButton = (Button) findViewById(R.id.startGameButton);
        mainFragment = new ActivityMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContainer, mainFragment).commit();
        loginDbManager = new LoginDbManager(MainActivity.this);

        //inicialiando mi el arreglo opciones
        opciones = new String[] {"Play","Settings",
                getResources().getString(R.string.info),
                getResources().getString(R.string.developers),
                getResources().getString(R.string.closeSession),
                getResources().getString(R.string.out)
                };
        drawerLayout= (DrawerLayout) findViewById(R.id.mainContainer) ;
        listView = (ListView) findViewById(R.id.leftMenu);

        //Seteamos el adapter del Listview que hace referencia a menuIzq
        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                letsPlayButton.setVisibility(View.GONE);

                AppInfoFragment fragment2 = null;
                DevelopersInfoFragment fragment3 = null;
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (position) {
                    case 0:
                        mainFragment = new ActivityMainFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, mainFragment)
                                .commit();
                        break;
                    case 1:
                        goSettings();
                        break;
                    case 2:
                        fragment2 = new AppInfoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, fragment2)
                                .commit();
                        break;
                    case 3:
                        fragment3 = new DevelopersInfoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, fragment3)
                                .commit();
                        break;
                    case 4:
                        closeSession();
                        break;
                    case 6:
                        closeApp();
                        break;
                    default:
                        mainFragment = new ActivityMainFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, mainFragment)
                                .commit();
                        break;

                }


                listView.setItemChecked(position, true);

                tituloSec = opciones[position];
                getSupportActionBar().setTitle(tituloSec);
                drawerLayout.closeDrawer(listView);


            }
        });

        //Obtenemos la referencia de los textos
        tituloSec=getTitle();
        //Colocar las acciones de abrir y cerrar el navigation drawer, vamos a utilizar un DrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.drawable.ic_action_action_subject,
                R.string.open,

                R.string.closed){

        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void goSettings(){
        Intent i = new Intent(this,SettingsActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    private void closeSession() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        loginDbManager.deleteData();
                        goLogin();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        PalabrasDbManager palabrasDbManager = new PalabrasDbManager(getApplicationContext());
                        List palabras = palabrasDbManager.getAllWords();
                        Toast.makeText(MainActivity.this, ""+palabras.get(10).toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.closeSure)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();

    }
    private void goLogin(){
        Intent i = new Intent(this,LoginActivity.class);
        finish();
        startActivity(i);
        }

    private void closeApp(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        System.exit(0);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.closeSure)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();

    }



    public void startGame(View view){
        Intent i = new Intent(this,DrawingActivity.class);
        finish();
        startActivity(i);
    }

}
