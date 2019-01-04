package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.gson.Forecast;
import com.coolweather.android.gson.Now;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Add_cityActivity extends AppCompatActivity {

    private ScrollView addCityLayout;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private LinearLayout forecastLayout;
    private LinearLayout.LayoutParams params;



    private Button add_back;
    private Button add_city;


    private String mWeatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);


        add_back = (Button)findViewById(R.id.back_to_main);
        add_city = (Button)findViewById(R.id.add_add_c);
        addCityLayout = (ScrollView) findViewById(R.id.weather_layout1);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh1);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        forecastLayout = (LinearLayout) findViewById(R.id.add_layout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);




        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii_3 = new Intent(Add_cityActivity.this,WeatherActivity.class);
                startActivity(ii_3);
            }
        });
        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=d8f43f5a1e78413a80ca06e94a7999ed";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Add_cityActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        }
                        else {
                            Toast.makeText(Add_cityActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                               }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Add_cityActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }




    } );}

    private void showWeatherInfo(Weather weather) {
        String cityName1 = weather.basic.cityName;
        String degree1 = weather.now.temperature + "℃";
        String weatherInfo1 = weather.now.more.info;


            View view = LayoutInflater.from(this).inflate(R.layout.add, forecastLayout, false);
            ImageView ic1 = (ImageView) view.findViewById(R.id.icon_add) ;
            TextView titleCity1 = (TextView) view.findViewById(R.id.add_c_name);
            TextView degreeText1 = (TextView) view.findViewById(R.id.add_degree_text);
            TextView weatherInfoText1 = (TextView) view.findViewById(R.id.add_weather_info_text);
            Button close = (Button)view.findViewById(R.id.add_delete);
            titleCity1.setText(cityName1);
            degreeText1.setText(degree1);
            weatherInfoText1.setText(weatherInfo1);

            forecastLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i_8 = new Intent(Add_cityActivity.this,WeatherActivity.class);
                    startActivity(i_8);



                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forecastLayout.removeView(view);

                }
            });




}


}

