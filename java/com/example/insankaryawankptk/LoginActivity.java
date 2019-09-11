package com.example.insankaryawankptk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initialization
    private static String URL_LOGIN = "http://192.168.1.143/KomunitasKPTK/Login.php";
    //Initialization

    //Session
        public final static String PKEY = "PKEY";
        public final static String TOKEN = "TOKEN";
        public final static String TYPE = "TYPE";
        public final static String NAME = "NAME";
        public final static String PNAME = "PNAME";
        String pkey, token, type, pname;
        SharedPreferences sharedpreferences;
        Boolean session = false;
        public static final String my_shared_preferences = "my_shared_preferences";
        public static final String session_status = "session_status";
    //Session

    //Hash
    public static final String hash(String s) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

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
    //Hash



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //Value User Interface Initialization
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        //Value User Interface Initialization

        //Spinner Dropdown
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Spinner Dropdown

        //Cek Session
        cekSession();
        //Cek Session

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAction();
            }

            private void LoginAction() {

                //Variable Initialization
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                //Security
                final String finaltoken = randomAlphaNumeric(200);
                final String emanp = randomAlphaNumeric(20);
                final String appsalt = "MjReaper";
                final String result_pkey = hash(finaltoken + username);
                final String result_token = hash2(appsalt + finaltoken + result_pkey);
                final String cek = hash2(appsalt + finaltoken + result_pkey + result_token);
                //Security

                sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                type = sharedpreferences.getString(TYPE, null);
                //Variable Initialization

                //Request to Retrieve Data
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int stt = jsonResponse.getInt("stt");
                            String token = jsonResponse.getString("token");

                            if(stt == 0){
                                if (cek.equals(token)){
                                    String name = jsonResponse.getString("name");
                                    String msg = jsonResponse.getString("msg");

                                    //Menyimpan Session
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean(session_status, true);
                                    editor.putString(PKEY, msg);
                                    editor.putString(NAME, name);
                                    editor.putString(TOKEN, token);
                                    editor.putString(PNAME, emanp);
                                    editor.commit();
                                    //Menyimpan Session

                                    if(type.equals("0")){
                                        Intent direct = new Intent(LoginActivity.this, Dashboard_KaryawanActivity.class);
                                        LoginActivity.this.startActivity(direct);
                                    }
                                    else if (type.equals("1")){
                                        Intent direct = new Intent(LoginActivity.this, Dashboard_PdiklatActivity.class);
                                        LoginActivity.this.startActivity(direct);
                                    }
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Username atau Password salah !")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Username atau Password salah !")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login Error 400 = " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Login Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                            }


                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("password", hash(password + "komunitaskptk"));
                        params.put("type", type);
                        params.put("token", finaltoken);
                        params.put("emanp", emanp);
                        params.put("cek", result_token);
                        return  params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
                //Request to Retrieve Data
            }
        });

    }

    private void cekSession() {

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);

        //Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        final String cek = hash2(pname + token + unique + hash);
        //Security

        final String URL_SESSION = "http://192.168.1.143/KomunitasKPTK/CekSession.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SESSION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int stt = jsonResponse.getInt("stt");
                    String generate = jsonResponse.getString("generate");

                    if(stt == 0){
                        if (cek.equals(generate)){
                            int type = jsonResponse.getInt("type");
                            if(type==0){
                                Intent intent = new Intent(LoginActivity.this, Dashboard_KaryawanActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            else if (type==1){
                                Intent intent = new Intent(LoginActivity.this, Dashboard_PdiklatActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }


                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pname", pname);
                params.put("token", token);
                params.put("unique", unique);
                params.put("cek", hash);
                return  params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }


    //Spinner Dropdown Responding to User Selections
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String type = adapterView.getItemAtPosition(i).toString();
        if (type.equals("Karyawan")){
            type = "0";
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(TYPE, type);
            editor.commit();
        }
        else if (type.equals("Peserta Diklat")){
            type = "1";
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(TYPE, type);
            editor.commit();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //Spinner Dropdown Responding to User Selections
}

