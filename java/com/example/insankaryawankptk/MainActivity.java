package com.example.insankaryawankptk;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/

public class MainActivity extends AppCompatActivity {

    //Session
    public final static String TYPE = "TYPE";
    String type;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    //Session

    //Permission
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    //Permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cek Session
        cekSession();
        //Cek Session

        //Permission
        requestStoragePermission();
        //Permission

        //Initialization
        final Button bLoginPage = (Button) findViewById(R.id.bLoginPage);
        //Initialization

        //Click Login Button
        bLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        //Click Login Button
    }

    private void cekSession() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        type = sharedpreferences.getString(TYPE, null);

        if(session){
            if (type.equals("0")){
                Intent intent = new Intent(MainActivity.this, Dashboard_KaryawanActivity.class);
                MainActivity.this.startActivity(intent);
            }
            else if (type.equals("1")){
                Intent intent = new Intent(MainActivity.this, Dashboard_PdiklatActivity.class);
                MainActivity.this.startActivity(intent);
            }
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}
