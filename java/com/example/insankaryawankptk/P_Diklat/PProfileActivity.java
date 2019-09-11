package com.example.insankaryawankptk.P_Diklat;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/

public class PProfileActivity extends AppCompatActivity {

    final static String URL_DATA = "http://192.168.1.143/KomunitasKPTK/MyProfile.php";

    //Session
    SharedPreferences sharedpreferences;
    public static final String PNAME = "PNAME";
    public static final String TOKEN = "TOKEN";
    String pname,token;
    //Session

    //Security
    String sha1 = "";
    public static final String hash2(String s){
        // With the java libraries
        String sha1 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(s.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return sha1;
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    //Security



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprofile);

        //Session
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);
        //Session

        //Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        final String cek = hash2(pname + token + unique + hash);
        //Security
        
        final TextView profile_name = (TextView) findViewById(R.id.profile_name);
        final TextView profile_type = (TextView) findViewById(R.id.profile_type);

        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/MyProfile.php" + "?pname=" + pname + "&token=" + token + "&unique=" + unique + "&cek=" + hash;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int stt = jsonResponse.getInt("stt");
                    String generate = jsonResponse.getString("generate");

                    if(stt == 0){
                        if (cek.equals(generate)){
                            String name = jsonResponse.getString("name");
                            int type = jsonResponse.getInt("type");
                            profile_name.setText(name);
                            if (type == 0){
                                profile_type.setText("Karyawan");
                            }
                            else if (type == 1){
                                profile_type.setText("Peserta Diklat");
                            }
                        }
                        else{
                            Toast.makeText(PProfileActivity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PProfileActivity.this);
                        builder.setMessage("Request Error !")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PProfileActivity.this, "Request Error 400 = " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PProfileActivity.this, "Request Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }


                });

        RequestQueue requestQueue = Volley.newRequestQueue(PProfileActivity.this);
        requestQueue.add(stringRequest);

    }
}
