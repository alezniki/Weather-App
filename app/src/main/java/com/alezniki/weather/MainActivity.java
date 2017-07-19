package com.alezniki.weather;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    // RequestQueue
    public static final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast";
    public static final String URL_COORDINATES = "?lat="; // ?lat=41.890251&lon=12.492373"; // Colosseum Rome
    public static final String URL_UNITS = "&units=metric";
    public static final String URL_API_KEY = "&APPID=YOUR_API_KEY_HERE";

    // Use Google API builder
    private GoogleApiClient googleApiClient;
    private final int PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       googleAPI();
    }

    public void downloadWeatherData(Location location) {

        // Full Latitude and Longitude GPS Coordinates
        final String latLon = URL_COORDINATES + location.getLatitude() + "&lon=" + location.getLongitude();

        // Request URL
        final String url = URL_BASE + latLon + URL_UNITS + URL_API_KEY;

        // Volley Library
        final JsonObjectRequest jsonRequest =
                new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("TAG", "RESPONSE: " + response.toString());

                        try {
                            // Grab the name and country from City Object {}
                            JSONObject city = response.getJSONObject("city");

                            String cityName = city.getString("name");
                            String country = city.getString("country");
                            Log.v("TAG", "City: " + cityName +", Country: " + country);

                            // Grab data from List Array []
                            JSONArray list = response.getJSONArray("list");

                            // Do the for loop to get items number
                            int cnt  = 10; // Number of lines returned by this API call

                            for (int i = 0; i < cnt ; i++) {

                                JSONObject listObject = list.getJSONObject(i);

                                // Grab the main object {} inside upper object from List array
                                JSONObject main = listObject.getJSONObject("main");
                                double temperature = main.getDouble("temp"); // Metric: Celsius
                                double tempMin = main.getDouble("temp_min"); // Metric: Celsius
                                double tempMax = main.getDouble("temp_max"); // Metric: Celsius
                                double pressure = main.getDouble("pressure"); // Atmospheric pressure hPa
                                int humidity = main.getInt("humidity"); // Humidity %

                                Log.v("TAG", "MAIN: Temperature: " + temperature + "˚C, Minimum temperature: "
                                        + tempMin + "˚C, Maximum temperature:" + tempMax +
                                        "˚C, Pressure: " + pressure + "hPa, Humidity: " + humidity + "%");

                                // Grab the weather array [] which is at the same level as main object, inside list object
                                JSONArray weatherArray = listObject.getJSONArray("weather");
                                JSONObject weatherObject = weatherArray.getJSONObject(0);// Index range [0..1)
                                String weatherMain = weatherObject.getString("main");
                                String description = weatherObject.getString("description");

                                Log.v("TAG", "WEATHER: Parameter: " + weatherMain + ", Condition: " + description);

                                // Grab the clouds object {}, on the same list level
                                JSONObject cloudsObject = listObject.getJSONObject("clouds");
                                String cloudiness = cloudsObject.getString("all"); // Cloudiness %

                                Log.v("TAG","CLOUDS: Cloudiness: " + cloudiness + "%");

                                // Grab the wind object {}, on the same list level
                                JSONObject windObject = listObject.getJSONObject("wind");
                                String speed = windObject.getString("speed"); // Wind speed degrees
                                String direction = windObject.getString("deg"); // Wind direction degrees

                                Log.v("TAG","WIND: Speed: " + speed + " deg, Direction: " + direction + "deg");

                                // Grab date  String from List Object
                                String rawDate = listObject.getString("dt_txt");

                                Log.v("TAG", "RAW DATE: " + rawDate);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("TAG", "JSON ERROR: " + e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("TAG", "RESPONSE ERROR: " + error.getLocalizedMessage());
                    }
                });

        // Make the request
        Volley.newRequestQueue(this).add(jsonRequest);
    }


    /**
     * .enableAutoManage(FragmentActivity, OnConnectionFailedListener)
     * Enables automatic lifecycle management in a support library FragmentActivity
     * that connects the client in onStart() and disconnects it in onStop().
     */
    public void googleAPI() {
        // Build Our Google Client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)  // Use location services API to get user location
                .enableAutoManage(this, this) // Automatically manage without extra code
                .addConnectionCallbacks(this) // Called when the client is connected or disconnected from the service
                .addOnConnectionFailedListener(this) // Callbacks for failed attempt to connect client to the service
                .build();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    // Called when Google services are connected
    @Override
    public void onConnected(Bundle bundle) {

        // If the user is not giving permission to use location we need to request those permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            Log.v("TAG", "Requesting Permissions");
        } else {
            // Start location services if the permission has already been given
            Log.v("TAG", "Starting Location Services From onConnected");
            startLocationServices();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // Called whenever location is changed, work with location itself
        downloadWeatherData(location);

    }
    public void startLocationServices() {
        Log.v("TAG", "Starting Location Services Called");
        try {
            LocationRequest locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            Log.v("TAG", "Requesting Location Updates");
        } catch (SecurityException exception) {
            Toast.makeText(this, "Please Enable Location Services Permission", Toast.LENGTH_LONG).show();
            Log.v("TAG", exception.toString());
        }
    }

    // Request permission which is going to call onRequestPermissionsResult
    // Throw a popup for user to give permission and grab permission from grantResults in switch statement
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // grantResults: the results of the permissions being granted
        switch (requestCode) {
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if more than zero permissions have been granted then we grab the first one and if permission  is granted we start location services
                    startLocationServices();
                    Log.v("TAG", "Permission Granted - Starting Services");
                } else {
                    // if its not been granted show a dialog to a user
                    Log.v("TAG", "Permission Not Granted");
                    Toast.makeText(this, "Weather can't run your location because you denied permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
