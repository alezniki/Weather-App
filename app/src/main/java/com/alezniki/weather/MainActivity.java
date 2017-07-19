package com.alezniki.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // RequestQueue
    public static final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast";
    public static final String URL_COORDINATES = "?lat=41.890251&lon=12.492373"; // Colosseum Rome
    public static final String URL_UNITS = "&units=metric";
    public static final String URL_API_KEY = "&APPID=YOUR_API_KEY_HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = URL_BASE+URL_COORDINATES+URL_UNITS+URL_API_KEY;

        // Volley Library
        final JsonObjectRequest jsonRequest =
                new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("TAG", "RESPONSE: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("TAG", "ERROR: " + error.getLocalizedMessage());
                    }
                });

        // Make the request
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
