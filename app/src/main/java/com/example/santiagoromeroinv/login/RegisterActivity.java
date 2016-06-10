package com.example.santiagoromeroinv.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagoromeroinv.R;
import com.example.santiagoromeroinv.database.UserDbManager;


public class RegisterActivity extends AppCompatActivity {

    private UserDbManager userDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDbManager = new UserDbManager(RegisterActivity.this);
    }

    public void onClickGoBack(View view){
        finish();
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }
    public void onClickRegisterUser(View view){

        TextView account = (TextView) findViewById(R.id.usernameRegisterField);
        TextView pass = (TextView) findViewById(R.id.userpassRegisterField);
        TextView passConfirm = (TextView) findViewById(R.id.confirmpassRegisterField);



        if(account.getText().toString().equals("")||pass.getText().toString().equals("")||passConfirm.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.mistake1), Toast.LENGTH_SHORT).show();
        }
        else if (!pass.getText().toString().equals(passConfirm.getText().toString())){
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.mistake4), Toast.LENGTH_SHORT).show();
        }
        else if (userDbManager.userExists(account.getText().toString())){
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.mistake5), Toast.LENGTH_SHORT).show();
        }
        else{
            userDbManager.insertUser(account.getText().toString(), pass.getText().toString());
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.mistake6), Toast.LENGTH_SHORT).show();
            account.setText("");
            pass.setText("");
            passConfirm.setText("");
        }
        finish();
    }
}
