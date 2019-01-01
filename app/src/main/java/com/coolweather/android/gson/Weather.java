package com.coolweather.android.gson;

import com.coolweather.android.Add_C;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    public List<Add_C> add_cs;
    public List<Now>  add_ss;

}
