package com.example.insankaryawankptk.Karyawan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.insankaryawankptk.LoginActivity;
import com.example.insankaryawankptk.Models.AbsentModel;
import com.example.insankaryawankptk.R;
import com.example.insankaryawankptk.Tools.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by muhjaury on 7/17/2019
 * Email : muhjaury@gmail.com
 **/

public class AbsensiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Absent Adapter Model
    private RequestQueue requestQueue;
    //Absent Adapter Model

    //Session
    SharedPreferences sharedpreferences;
    public static final String PNAME = "PNAME";
    public static final String TOKEN = "TOKEN";
    String pname, token;
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
        setContentView(R.layout.activity_absensi);

        //Absent Adapter Model
        requestQueue = Volley.newRequestQueue(this);
        //Absent Adapter Model

        //Session
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        pname = sharedpreferences.getString(PNAME, null);
        token = sharedpreferences.getString(TOKEN, null);
        //Session

        Button button = (Button) findViewById(R.id.absensi_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(c.getTime());

        final TextView date = (TextView) findViewById(R.id.absensi_tv);
        final TextView in = (TextView) findViewById(R.id.absensi_timein);
        final TextView out = (TextView) findViewById(R.id.absensi_timeout);
        date.setText(strDate);

        JsonArrayRequest jsonArrayRequestn = new JsonArrayRequest(prepareGetMethodUrl(strDate), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i < response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String absentArrive = jsonObject.getString("min");
                        String absentReturn = jsonObject.getString("max");
                        in.setText(absentArrive);
                        out.setText(absentReturn);

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AbsensiActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonArrayRequestn);
    }

    private String prepareGetMethodUrl(String date) {

        //Security
        final String unique = randomAlphaNumeric(100);
        final String hash = hash2(pname + token + unique);
        //Security

        final String URL_DATA = "http://192.168.1.143/KomunitasKPTK/GetAbsent.php";
        return URL_DATA + "?pname=" + pname + "&token=" + token + "&unique=" + unique + "&cek=" + hash + "&date=" + date ;
    }
}
