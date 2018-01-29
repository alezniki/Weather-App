package com.alezniki.weather.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alezniki.weather.NetworkConnectionReceiver;
import com.alezniki.weather.R;
import com.alezniki.weather.adapters.WeatherAdapter;
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

/**
 * Main activity
 * <p>
 * RequestQueue: 16 day weather forecast
 *
 * @author Nikola Aleksic
 */
@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    /**
     * Url base
     */
    private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast/daily";

    /**
     * Url coordinates
     */
    private static final String URL_COORDINATES = "?lat=";

    /**
     * Url units
     */
    private static final String URL_UNITS = "&units=metric";

    /**
     * Url api key
     */
    private static final String URL_API_KEY = "&APPID=YOUR_API_KEY_HERE";

    /**
     * Google api client
     */
    private GoogleApiClient googleApiClient;

    /**
     * Permission location
     */
    private static final int PERMISSION_LOCATION = 1;

    /**
     * Weather adapter
     */
    private WeatherAdapter adapter;

    /**
     * Recycler view
     */
    private RecyclerView recycler;

    /**
     * Layout manager
     */
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Weather data
     */
    private WeatherData weatherData;

    /**
     * Weather data list
     */
    private List<WeatherData> weathers;

    /**
     * Network connection receiver
     */
    private NetworkConnectionReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Construct the data source
        weathers = new ArrayList<>();

        //Create the adapter to convert the array to views
        recycler = (RecyclerView) findViewById(R.id.recycler);

        //1.Improve RecyclerView performance
        recycler.setHasFixedSize(true);

        //2.Use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        //3.Create the adapter to convert the array to views
        adapter = new WeatherAdapter(this, weathers);
        recycler.setAdapter(adapter);

        googleAPI();
        receiver = new NetworkConnectionReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recycler != null && adapter != null) {
            adapter.clearDataFromAdapter();

            adapter.addDataToAdapter(weathers);
            adapter.notifyDataSetChanged();
        }

        enableLocation();

        IntentFilter customFilter = new IntentFilter(NetworkConnectionReceiver.NOTIFY_NETWORK_CHANGE);
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, customFilter);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (receiver != null) {
            //Unregister broadcast receiver
            unregisterReceiver(receiver);
        }
    }

    /**
     * Download Weather Data
     *
     * @param location location
     */
    private void downloadWeatherData(Location location) {

        //Full Latitude and Longitude GPS Coordinates
        final String latLon = URL_COORDINATES + location.getLatitude() + "&lon=" + location.getLongitude();

        //Request URL
        final String url = URL_BASE + latLon + URL_UNITS + URL_API_KEY;

        //Volley Library
        final JsonObjectRequest jsonRequest =
                new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Grab the name and country from City Object {}
                            JSONObject cityObject = response.getJSONObject("city");

                            String cityName = cityObject.getString("name");
                            String country = cityObject.getString("country");
                            System.out.println("######## CITY: " + cityName + ", COUNTRY: " + country);

                            //Grab data from List Array []
                            JSONArray listArray = response.getJSONArray("list");

                            //Do the for loop to get items number (counts returned by this API call)
                            int count = 7;

                            for (int i = 0; i < count; i++) {

                                JSONObject listObject = listArray.getJSONObject(i);

                                //Grab the main object {} inside upper object from List array
                                JSONObject tempObject = listObject.getJSONObject("temp");

                                //Daily averaged temperature
                                double dayTemp = tempObject.getDouble("day");

                                //Night temperature
                                double nightTemp = tempObject.getDouble("night");

                                //Evening temperature
                                double eveningTemp = tempObject.getDouble("eve");

                                //Morning temperature
                                double morningTemp = tempObject.getDouble("morn");

                                //Atmospheric pressure hPa
                                double pressure = listObject.getDouble("pressure");

                                //Humidity %
                                int humidity = listObject.getInt("humidity");

                                //Grab the weather array [] which is at the same level as main object, inside list object
                                JSONArray weatherArray = listObject.getJSONArray("weather");

                                //Index range [0..1)
                                JSONObject weatherObject = weatherArray.getJSONObject(0);

                                //Main weather
                                String mainWeather = weatherObject.getString("main");

                                //Weather description
                                String weatherDescription = weatherObject.getString("description");

                                //Grab the clouds object {}, on the same list level
                                //Cloudiness %
                                int clouds = listObject.getInt("clouds");

                                //Grab the wind object {}, on the same list level
                                //Wind speed degrees
                                double windSpeed = listObject.getDouble("speed");

                                //Wind direction degrees
                                double windDirection = listObject.getDouble("deg");

                                //Grab date string from list object
                                int rawDate = listObject.getInt("dt");

                                @SuppressLint("SimpleDateFormat")
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(rawDate * 1000L));

                                weatherData = new WeatherData(cityName, country, dayTemp, nightTemp, eveningTemp, morningTemp, pressure, humidity, mainWeather, weatherDescription, clouds, windSpeed, windDirection, date);
                                refreshUI();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("######## JSON ERROR: " + e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                        System.out.println("######## RESPONSE ERROR: " + e.getLocalizedMessage());
                    }
                });

        //Make the Volley request
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    /**
     * Google API
     * <p>
     * .enableAutoManage(FragmentActivity, OnConnectionFailedListener)
     * Enables automatic lifecycle management in a support library FragmentActivity
     * that connects the client in onStart() and disconnects it in onStop().
     */
    private void googleAPI() {

        //Build google client
        googleApiClient = new GoogleApiClient.Builder(this)

                //Use location services API to get user location
                .addApi(LocationServices.API)

                //Automatically manage without extra code
                .enableAutoManage(this, this)

                //Called when the client is connected or disconnected from the service
                .addConnectionCallbacks(this)

                //Callbacks for failed attempt to connect client to the service
                .addOnConnectionFailedListener(this).build();
    }

    /**
     * Refresh UI data
     */
    private void refreshUI() {
        weathers.add(weatherData);
        adapter.notifyDataSetChanged();
    }

    /**
     * Enable Location
     */
    private void enableLocation() {

        //Make sure that GPS is enabled on the device
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Snackbar snack = Snackbar.make(findViewById(R.id.recycler), R.string.snackbar_message_location, Snackbar.LENGTH_INDEFINITE);
            snack.setAction(R.string.snackbar_action_location, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                }
            });

            snack.show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        //Called when Google services are connected

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //If the user is not giving permission to use location we need to request those permissions
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            System.out.println("######## REQUEST PERMISSIONS FOR LOCATION SERVICES");
        } else {
            //Start location services if the permission has already been given
            startLocationServices();
            System.out.println("######## START LOCATION SERVICES");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //Called whenever location is changed, work with location itself
        downloadWeatherData(location);
    }

    /**
     * Start Location Services
     */
    private void startLocationServices() {

        try {
            LocationRequest locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            System.out.println("######## REQUEST LOCATION UPDATES");
        } catch (SecurityException exception) {
            Toast.makeText(this, R.string.toast_location_services, Toast.LENGTH_LONG).show();
            System.out.println("######## SECURITY EXCEPTION" + exception.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Request permission which is going to call onRequestPermissionsResult
        //Throw a popup for user to give permission and grab permission from grantResults in switch statement
        //grantResults: the results of the permissions being granted

        if (requestCode == PERMISSION_LOCATION) {

            //If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("######## PERMISSION GRANTED - START SERVICE");

                //if more than zero permissions have been granted then we grab the first one and if permission  is granted we start location services
                startLocationServices();
            } else {
                System.out.println("######## PERMISSION NOT GRANTED");

                //If its not been granted show notification to user to enable location
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    Snackbar snack = Snackbar.make(findViewById(R.id.recycler), R.string.snackbar_message_permission, Snackbar.LENGTH_LONG);
                    snack.setAction(R.string.snackbar_action_permission, new View.OnClickListener() {
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

    /**
     * Check And Request Permissions
     * <p>
     * Check if user denied location and request permission
     *
     * @return permission
     */
    private void checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return;
        }
    }

    //Broadcast Receiver to refresh UI when connected back to network
    private final BroadcastReceiver localReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnected = intent.getBooleanExtra(NetworkConnectionReceiver.EXTRA_IS_CONNECTED, true);

            if (!isConnected) {
                checkNetworkDialog();
            }
        }
    };

    /**
     * Check Network Dialog
     * <p>
     * Show dialog to the user if there is no internet connection
     */
    public void checkNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.network_dialog_title);
        builder.setMessage(R.string.network_dialog_message);

        builder.setPositiveButton(R.string.network_dialog_button_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
