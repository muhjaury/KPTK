package com.example.insankaryawankptk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.insankaryawankptk.P_Diklat.DiklatActivity;
import com.example.insankaryawankptk.P_Diklat.PProfileActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.Adapters.RSSAdapter;
import com.example.insankaryawankptk.Models.RSSModel;
import com.example.insankaryawankptk.P_Diklat.HistoryDiklatActivity;
import com.example.insankaryawankptk.P_Diklat.SertifikatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/

public class Dashboard_PdiklatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RSSAdapter.OnItemClickListener {

    //RSS
    private RecyclerView recyclerView;
    private RSSAdapter rssAdapter;
    private ArrayList<RSSModel> rssModels;
    private RequestQueue requestQueue;
    //RSS

    //Session
    SharedPreferences sharedpreferences;
    public static final String PKEY = "PKEY";
    public static final String PNAME = "PNAME";
    public static final String TOKEN = "TOKEN";
    public final static String NAME = "NAME";
    String pname, token, name;
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

    //Back Button
    boolean doubleBackToExitPressedOnce = false;
    //Back Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__pdiklat);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //S: Nav_Header
        View headview = navigationView.getHeaderView(0);
        TextView name_user = headview.findViewById(R.id.tvname);
        //E: Nav_Header

        //Session
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);
        name = sharedpreferences.getString(NAME, null);
        //Session


        //S: User
        name_user.setText(name);
        //E: User

        //RSS
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rssModels = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        parseJSON();
        //RSS

    }

    private void parseJSON() {

        String url = "https://kptk.or.id/?mod=getnews";
        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0;i < 5;i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String txtTitle = jsonObject.getString("title");
                                String imageView = jsonObject.getString("img");
                                String txtDesc = jsonObject.getString("desc");
                                String txtUrl = jsonObject.getString("url");

                                rssModels.add(new RSSModel(txtTitle, txtDesc, txtUrl, imageView));
                                rssAdapter = new RSSAdapter(Dashboard_PdiklatActivity.this, rssModels);
                                recyclerView.setAdapter(rssAdapter);
                                rssAdapter.setOnItemClickListener(Dashboard_PdiklatActivity.this);
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Dashboard_PdiklatActivity.this, "Error 200 = " + e.toString(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dashboard_PdiklatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(jsonArrayRequestn);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(LoginActivity.session_status, false);
            editor.putString(PKEY, null);
            editor.putString(PNAME, null);
            editor.putString(NAME, null);
            editor.putString(TOKEN, null);
            editor.commit();
            dropUserData();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard__pdiklat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(PKEY, null);
                editor.putString(PNAME, null);
                editor.putString(NAME, null);
                editor.putString(TOKEN, null);
                editor.commit();

                dropUserData();
                finish();
                super.onBackPressed();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dropUserData() {
        String URL_DROPUSERDATA = "http://192.168.1.143/KomunitasKPTK/DropUserData.php";
        //Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        final String cek = hash2(pname + token + unique + hash);
        //Security
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DROPUSERDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int stt = jsonResponse.getInt("stt");
                    String generate = jsonResponse.getString("generate");

                    if(stt == 0){
                        if(cek.equals(generate)){
                            String desc = jsonResponse.getString("desc");
                        }
                        else {
                            Toast.makeText(Dashboard_PdiklatActivity.this, "Failure !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard_PdiklatActivity.this);
                        builder.setMessage("Delete Error !")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Dashboard_PdiklatActivity.this, "Delete Error 400 =  " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dashboard_PdiklatActivity.this, "Delete Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard_PdiklatActivity.this);
        requestQueue.add(stringRequest);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.profile:
                Intent p = new Intent(Dashboard_PdiklatActivity.this, PProfileActivity.class);
                startActivity(p);
                break;
            case R.id.history_diklat:
                Intent hd = new Intent(Dashboard_PdiklatActivity.this, HistoryDiklatActivity.class);
                startActivity(hd);
                break;
            case R.id.sertifikat:
                Intent s = new Intent(Dashboard_PdiklatActivity.this, SertifikatActivity.class);
                startActivity(s);
                break;
            case R.id.coming_soon_diklat:
                Intent csd = new Intent(Dashboard_PdiklatActivity.this, DiklatActivity.class);
                csd.putExtra("option","Coming Soon");
                startActivity(csd);
                break;
            case R.id.on_going_diklat:
                Intent ogd = new Intent(Dashboard_PdiklatActivity.this, DiklatActivity.class);
                ogd.putExtra("option", "On Going");
                startActivity(ogd);
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onItemClick(int position) {
        RSSModel clickedItem = rssModels.get(position);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(clickedItem.getTxtUrl()));

        startActivity(intent);
    }
}
