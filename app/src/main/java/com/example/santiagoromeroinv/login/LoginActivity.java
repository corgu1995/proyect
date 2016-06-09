package com.example.santiagoromeroinv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagoromeroinv.database.LoginDbManager;
import com.example.santiagoromeroinv.database.UserDbManager;
import com.example.santiagoromeroinv.R;
import com.example.santiagoromeroinv.main.MainActivity;


public class LoginActivity extends AppCompatActivity {

    private CheckBox saveLogin = null;

    private static final int REQUEST_CODE=10;
    UserDbManager userDbManager = null;
    LoginDbManager loginDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDbManager = new UserDbManager(LoginActivity.this);
        loginDbManager = new LoginDbManager(LoginActivity.this);
        ImageView iv= (ImageView)findViewById(R.id.imageView);

        boolean userLogged = loginDbManager.isUserLogged();
        if (userLogged){
            Toast.makeText(LoginActivity.this, "Bienvenido "+loginDbManager.getUserAccount(), Toast.LENGTH_SHORT).show();
            goToMain();
        }
    }

    public void onClickRegister(View view){
        clearFields();
        Intent i = new Intent(this,RegisterActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void onClickLogin(View view){
        TextView account = (TextView) findViewById(R.id.userLoginField);
        TextView password = (TextView) findViewById(R.id.passwordLoginField);
        saveLogin = (CheckBox) findViewById(R.id.rememberLoginCheckbox);

        if (account.getText().toString().equals("")||password.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.mistake1), Toast.LENGTH_SHORT).show();
        }
        else if (checkFields(account.getText().toString(), password.getText().toString())){
            if (saveLogin.isChecked()){
                loginDbManager.insertUserAccount(account.getText().toString());
            }
            clearFields();
            goToMain();
        }
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }

    private void clearFields() {
        TextView account = (TextView) findViewById(R.id.userLoginField);
        TextView password = (TextView) findViewById(R.id.passwordLoginField);

        account.setText("");
        password.setText("");
    }

    private boolean checkFields(String account, String pass) {
        boolean findAccount = userDbManager.userExists(account);
        if(!findAccount){
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.mistake2), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (userDbManager.getUserAccount(account).equals(account) && userDbManager.getUserPassword(account).equals(pass)) {
            return true;
        } else {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.mistake3), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
