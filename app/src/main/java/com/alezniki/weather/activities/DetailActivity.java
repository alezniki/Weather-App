package com.alezniki.weather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.alezniki.weather.R;
import com.alezniki.weather.adapters.DetailWeatherAdapter;
import com.alezniki.weather.model.WeatherData;

import java.util.ArrayList;
import java.util.List;

import static com.alezniki.weather.adapters.WeatherAdapter.KEY_ID;

/**
 * Detail activity
 *
 * @author Nikola Aleksic
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        //Receive the object that which has been sent through Intent from MainActivity
        WeatherData weatherData = (WeatherData) getIntent().getSerializableExtra(KEY_ID);

        //Construct data source
        List<WeatherData> weathers = new ArrayList<>();

        //Create the adapter to convert array to views
        DetailWeatherAdapter adapter = new DetailWeatherAdapter(this, weathers);

        //Attach the adapter to a listView and set adapter
        ListView listView = (ListView) findViewById(R.id.lv_detail_list_items);
        listView.setAdapter(adapter);

        weathers.add(weatherData);
        adapter.notifyDataSetChanged();
    }
}
