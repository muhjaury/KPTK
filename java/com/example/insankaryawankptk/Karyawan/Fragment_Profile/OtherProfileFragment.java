package com.example.insankaryawankptk.Karyawan.Fragment_Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.Adapters.OtherProfileAdapter;
import com.example.insankaryawankptk.Dashboard_PdiklatActivity;
import com.example.insankaryawankptk.Karyawan.SysInfoActivity;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.Models.OtherProfileModel;
import com.example.insankaryawankptk.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhjaury on 8/2/2019.
 * Email : muhjaury@gmail.com
 */
public class OtherProfileFragment extends Fragment{

    //Session
    SharedPreferences sharedPreferences;
    public static final String PNAME = "PNAME";
    public static final String TOKEN = "TOKEN";
    String pname,token;
    //Session

    //Diklat Adapter Model
    private OtherProfileAdapter otherProfileAdapter;
    private ArrayList<OtherProfileModel> otherProfileModels;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    //Diklat Adapter Model

    static String nama = "You are not our USER";
    static String id = "You are not our USER";

    //Security
    String sha1 = "jkaSDDFdkjdgdh3HLGS3LGJ3OGNdfsLKGN";
    String cek = "NSLD7JcGHL3H375GOkgjhgL8Hnkjbob8237539432";
    String unique = "HJGJ669677gkjg65r9G8YG9G0G9t0gYUG8G8G8G9";
    String hash = "gkjg87t708579R6FUYT567ty0hn980hG8ygb987Y58";
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);

        //Session
        sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedPreferences.getString(PNAME, null);
        token = sharedPreferences.getString(TOKEN, null);
        //Session

        //Advanced Security
        unique = randomAlphaNumeric(100);
        hash = hash2(pname + token + unique);
        cek = hash2(pname + token + unique + hash);
        //Advanced Security

        //OtherProfile Adapter Model
        recyclerView = view.findViewById(R.id.rv_otherprofile);
        otherProfileModels = new ArrayList<>();

        parseJSON();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //OtherProfile Adapter Model

        return view;
    }

    private void parseJSON() {
        String URL = "http://192.168.1.143/KomunitasKPTK/OtherProfile.php";
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(prepareGetMethodUrl(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i < response.length();i++){
                    try {
                        JSONObject jsonResponse = response.getJSONObject(i);

                        nama = jsonResponse.getString("name");
                        id = jsonResponse.getString("id");

                        otherProfileModels.add(new OtherProfileModel(nama,id));
                        otherProfileAdapter = new OtherProfileAdapter(getActivity().getApplicationContext(), otherProfileModels);
                        recyclerView.setAdapter(otherProfileAdapter);
                        otherProfileAdapter.setOnItemClickListener(new OtherProfileAdapter.OnItemClickListener(){
                            public void onItemClick(int position) {
                                OtherProfileModel clickedItem = otherProfileModels.get(position);
                                //Buat Halaman Detail untuk User Lain

                                //Intent intent = new Intent(getActivity().getApplicationContext(), SysInfoActivity.class);
                                //intent.putExtra("NAMA", clickedItem.getNama());
                                //intent.putExtra("ID", clickedItem.getId());

                                //startActivity(intent);
                            }
                        });

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection " + error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonArrayRequestn);

    }

    private String prepareGetMethodUrl() {
        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/OtherProfile.php";
        return URL_DATA + "?pname=" + pname + "&token=" + token + "&unique=" + unique + "&cek=" + hash;
    }
}
