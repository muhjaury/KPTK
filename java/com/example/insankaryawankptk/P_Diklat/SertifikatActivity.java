package com.example.insankaryawankptk.P_Diklat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.Adapters.SertifikatAdapter;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.Models.SertifikatModel;
import com.example.insankaryawankptk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class SertifikatActivity extends AppCompatActivity implements SertifikatAdapter.OnItemClickListener {

    //Sertifikat Adapter Model
    private RecyclerView recyclerView;
    private SertifikatAdapter sertifikatAdapter;
    private ArrayList<SertifikatModel> sertifikatModel;
    private RequestQueue requestQueue;
    //Sertifikat Adapter Model

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
        setContentView(R.layout.activity_sertifikat);

        //Session
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);
        //Session

        //Sertifikat Adapter Model
        recyclerView = findViewById(R.id.rv_sertifikat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sertifikatModel = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        parseJSON();
        //Sertifikat Adapter Model
    }

    private void parseJSON() {
        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(prepareGetMethodUrl(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String sertifikatTitle = jsonObject.getString("judul");
                        String sertifikatURL = jsonObject.getString("certificate_url");

                        sertifikatModel.add(new SertifikatModel(sertifikatTitle, sertifikatURL));
                        sertifikatAdapter = new SertifikatAdapter(SertifikatActivity.this, sertifikatModel);
                        recyclerView.setAdapter(sertifikatAdapter);
                        sertifikatAdapter.setOnItemClickListener(SertifikatActivity.this);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SertifikatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonArrayRequestn);
    }

    private String prepareGetMethodUrl() {

        //Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        //Security

        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/GetSertifikat.php";
        return URL_DATA + "?pname=" + pname + "&token=" + token + "&unique="+ unique + "&cek=" + hash;
    }

    @Override
    public void onItemClick(int position) {
        SertifikatModel clickedItem = sertifikatModel.get(position);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(clickedItem.getSertifikatURL()));

        startActivity(intent);
    }
}
