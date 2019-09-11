package com.example.insankaryawankptk.P_Diklat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.Adapters.DiklatAdapter;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.Models.DiklatModel;
import com.example.insankaryawankptk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class DiklatActivity extends AppCompatActivity {

    //Diklat Adapter Model
    private RecyclerView recyclerView;
    private DiklatAdapter diklatAdapter;
    private ArrayList<DiklatModel> diklatModels;
    private RequestQueue requestQueue;
    //Diklat Adapter Model

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
        setContentView(R.layout.activity_diklat);

        Bundle extra = (Bundle) getIntent().getExtras();
        String get = extra.getString("option");

        //Diklat Adapter Model
        recyclerView = findViewById(R.id.rv_diklat_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diklatModels = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        //Diklat Adapter Model

        //Session
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);
        //Session


        if (get.equals("On Going")){
            onGoing();
        }
        else if (get.equals("Coming Soon")){
            comingSoon();
        }
        else{
            Toast.makeText(this, "Invalid Request !", Toast.LENGTH_SHORT).show();
        }

    }

    private void onGoing() {
        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(onGoingMethod(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String diklatTitle = jsonObject.getString("judul");
                        String diklatTempat = jsonObject.getString("tempat");
                        String diklatDesc = jsonObject.getString("desc");
                        String diklatStart = jsonObject.getString("tgl_mulai");
                        String diklatEnd = jsonObject.getString("tgl_akhir");
                        String diklatOrganizer = jsonObject.getString("organizer");

                        diklatModels.add(new DiklatModel(diklatTitle, diklatTempat, diklatDesc, diklatStart, diklatEnd, diklatOrganizer));
                        diklatAdapter = new DiklatAdapter(DiklatActivity.this, diklatModels);
                        recyclerView.setAdapter(diklatAdapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DiklatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonArrayRequestn);
    }

    private String onGoingMethod() {

        //Advanced Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        //Advanced Security

        final String date = "ongoing";
        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/GetDiklat.php";
        return URL_DATA + "?date=" + date + "&pname=" + pname + "&token=" + token + "&unique="+ unique + "&cek=" + hash;
    }

    private void comingSoon() {
        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(comingSoonMethod(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String diklatTitle = jsonObject.getString("judul");
                        String diklatTempat = jsonObject.getString("tempat");
                        String diklatDesc = jsonObject.getString("desc");
                        String diklatStart = jsonObject.getString("tgl_mulai");
                        String diklatEnd = jsonObject.getString("tgl_akhir");
                        String diklatOrganizer = jsonObject.getString("organizer");

                        diklatModels.add(new DiklatModel(diklatTitle, diklatTempat, diklatDesc, diklatStart, diklatEnd, diklatOrganizer));
                        diklatAdapter = new DiklatAdapter(DiklatActivity.this, diklatModels);
                        recyclerView.setAdapter(diklatAdapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DiklatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonArrayRequestn);
    }

    private String comingSoonMethod() {

        //Advanced Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        //Advanced Security

        final String date = "comingsoon";
        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/GetDiklat.php";
        return URL_DATA + "?date=" + date + "&pname=" + pname + "&token=" + token + "&unique="+ unique + "&cek=" + hash;
    }

}
