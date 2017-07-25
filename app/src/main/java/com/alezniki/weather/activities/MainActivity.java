package com.alezniki.weather.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alezniki.weather.R;
import com.alezniki.weather.WeatherAdapter;
import com.alezniki.weather.model.WeatherData;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
       implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    // RequestQueue : 16 day weather forecast
    public static final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast/daily";
    public static final String URL_COORDINATES = "?lat="; // ?lat=41.890251&lon=12.492373"; // Colosseum Rome
    public static final String URL_UNITS = "&units=metric";
    public static final String URL_API_KEY = "&APPID=YOUR_API_KEY_HERE";

    // Use Google API builder
    private GoogleApiClient googleApiClient;
    private final int PERMISSION_LOCATION = 1;

    // RecyclerView
    private WeatherAdapter adapter;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;

    // Weather Data
    private WeatherData wd;
    private List<WeatherData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Construct the data source
        list = new ArrayList<>();
        // Create the adapter to convert the array to views
        recycler = (RecyclerView) findViewById(R.id.recycler);

        // 1. Improve RecyclerView performance
        recycler.setHasFixedSize(true);
        // 2. Use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        //3. Create the adapter to convert the array to views
        adapter = new WeatherAdapter(list, this);
        recycler.setAdapter(adapter);

        googleAPI();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null && recycler != null) {
            adapter.clearDataFromAdapter();
//            adapter.notifyDataSetChanged();
        }

        adapter.notifyDataSetChanged();



//        // Make sure that GPS is enabled on the device
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //allowLocationPermission();
            Snackbar snack =  Snackbar.make(findViewById(R.id.recycler),"Please enable location", Snackbar.LENGTH_INDEFINITE);
            snack.setAction("Enable", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                }
            });
            snack.show();
        }

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
                            JSONObject cityObject = response.getJSONObject("city");

                            String cityName = cityObject.getString("name");
                            String country = cityObject.getString("country");
                            Log.v("TAG", "City: " + cityName + ", Country: " + country);

                            // Grab data from List Array []
                            JSONArray listArray = response.getJSONArray("list");

                            // Do the for loop to get items number
                            int cnt = 7; // Number of lines returned by this API call

                            for (int i = 0; i < cnt; i++) {

                                JSONObject listObject = listArray.getJSONObject(i);

                                // Grab the main object {} inside upper object from List array
                                JSONObject tempObject = listObject.getJSONObject("temp");
                                double dayTemp = tempObject.getDouble("day"); // Daily averaged temperature
                                double minTemp = tempObject.getDouble("min"); // Min daily temperature
                                double maxTemp = tempObject.getDouble("max"); // Max daily temperature
                                double nightTemp = tempObject.getDouble("night"); // Night temperature
                                double eveningTemp = tempObject.getDouble("eve"); // Evening temperature
                                double morningTemp = tempObject.getDouble("morn"); // Morning temperature

                                double pressure = listObject.getDouble("pressure"); // Atmospheric pressure hPa
                                int humidity = listObject.getInt("humidity"); // Humidity %

                                Log.v("TAG", "MAIN: Day: " + dayTemp + " ˚C, Min: " + minTemp
                                        + "˚C, Max:" + maxTemp + " ˚C, Night: " + nightTemp
                                        + "˚C, Evening:" + eveningTemp + " ˚C, Morning: "
                                        + morningTemp + " ˚C, Pressure: " + pressure
                                        + " hPa, Humidity: " + humidity + "%");

                                // Grab the weather array [] which is at the same level as main object, inside list object
                                JSONArray weatherArray = listObject.getJSONArray("weather");
                                JSONObject weatherObject = weatherArray.getJSONObject(0);// Index range [0..1)
                                String mainWeather = weatherObject.getString("main");
                                String weatherDescription = weatherObject.getString("description");

                                Log.v("TAG", "WEATHER: Parameter: " + mainWeather + ", Condition: " + weatherDescription);

                                // Grab the clouds object {}, on the same list level
                                int clouds = listObject.getInt("clouds"); // Cloudiness %

                                Log.v("TAG", "CLOUDS: " + clouds + " %");

                                // Grab the wind object {}, on the same list level
                                double windSpeed = listObject.getDouble("speed"); // Wind speed degrees
                                double windDirection = listObject.getDouble("deg"); // Wind direction degrees

                                Log.v("TAG", "WIND: Speed: " + windSpeed + " meter/sec, Direction: " + windDirection + "deg");

                                // Grab date  String from List Object
                                int rawDate = listObject.getInt("dt");
                                Log.v("TAG", "RAW DATE: " + rawDate);

                                String date = new SimpleDateFormat("yyyy-MM-dd")
                                        .format(new Date(rawDate * 1000L));
                                Log.v("TAG", "DATE: " + date);

                                wd = new WeatherData();
                                wd.setCityName(cityName);
                                wd.setCountry(country);
                                wd.setDayTemp((int) dayTemp);
                                wd.setMinTemp((int) minTemp);
                                wd.setMaxTemp((int) maxTemp);
                                wd.setNightTemp((int) nightTemp);
                                wd.setEveningTemp((int) eveningTemp);
                                wd.setMorningTemp((int) morningTemp);
                                wd.setPressure((int) pressure);
                                wd.setHumidity(humidity);
                                wd.setMainWeather(mainWeather);
                                wd.setWeatherDescription(weatherDescription);
                                wd.setClouds(clouds);
                                wd.setWindSpeed((int) windSpeed);
                                wd.setWindDirection((int) windDirection);
                                wd.setDate(date);

                                refreshUI();

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

        // Make the Volley request
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

    public void refreshUI() {
        list.add(wd);
        adapter.notifyDataSetChanged(); // // Refresh data
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
        if (requestCode == PERMISSION_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if more than zero permissions have been granted then we grab the first one and if permission  is granted we start location services
                startLocationServices();

                Log.v("TAG", "Permission Granted - Starting Services");
            } else {
                // if its not been granted show a dialog to a user requesting that GPS be enabled
                Log.v("TAG", "Permission Not Granted");
                Toast.makeText(this, "Weather can't run your location because you denied permission", Toast.LENGTH_LONG).show();

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    Snackbar snack =  Snackbar.make(findViewById(R.id.recycler),"Allow Permission", Snackbar.LENGTH_INDEFINITE);
                    snack.setAction("Request", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkAndRequestPermissions();

                        }
                    });
                    snack.show();
                }

            }
        }
    }


    private  boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
            return false;
        }
        return true;
    }
}
