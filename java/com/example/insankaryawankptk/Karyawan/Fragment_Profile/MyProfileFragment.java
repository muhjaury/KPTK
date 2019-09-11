package com.example.insankaryawankptk.Karyawan.Fragment_Profile;

import android.content.Context;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.Adapters.MyProfileAdapter;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.Models.MyProfileModel;
import com.example.insankaryawankptk.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by muhjaury on 8/2/2019.
 * Email : muhjaury@gmail.com
 */
public class MyProfileFragment extends Fragment {

    //Session
    SharedPreferences sharedPreferences;
    public static final String PNAME = "PNAME";
    public static final String TOKEN = "TOKEN";
    String pname,token;
    //Session

    //Diklat Adapter Model
    private MyProfileAdapter myProfileAdapter;
    private ArrayList<MyProfileModel> myProfileModels;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    //Diklat Adapter Model

    static String final_tipe = "You are not our USER";
    static String nama = "You are not our USER";

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
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

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

        //MyProfile Adapter Model
        recyclerView = view.findViewById(R.id.rv_myprofile);
        myProfileModels = new ArrayList<>();

        parseJSON();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //MyProfile Adapter Model

        return view;
    }

    private void parseJSON() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(prepareGetMethodUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int stt = jsonResponse.getInt("stt");
                    String generate = jsonResponse.getString("generate");

                    if(stt == 0){
                        if (cek.equals(generate)){
                            nama = jsonResponse.getString("name");
                            String tipe = jsonResponse.getString("type");
                            if (tipe.equals("0")){
                                final_tipe = "Karyawan";
                            }
                            else if (tipe.equals("1")){
                                final_tipe = "Peserta Diklat";
                            }
                        }
                        else{
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Token", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                    }

                    myProfileModels.add(new MyProfileModel(nama,final_tipe));
                    myProfileAdapter = new MyProfileAdapter(getActivity().getApplicationContext(), myProfileModels);
                    recyclerView.setAdapter(myProfileAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Request Error 400 = " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection " + error, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
        requestQueue.add(stringRequest);

    }

    private String prepareGetMethodUrl() {
        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/MyProfile.php";
        return URL_DATA + "?pname=" + pname + "&token=" + token + "&unique=" + unique + "&cek=" + hash;
    }

}
