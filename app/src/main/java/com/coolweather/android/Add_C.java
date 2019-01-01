package com.coolweather.android;

import com.coolweather.android.gson.Now;
import com.google.gson.annotations.SerializedName;

public class Add_C {
   /* private int imageId;
    private String city_name;
    private String city_degree;
    private String city_weather;*/
    @SerializedName("city")
    public String cityName;

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public Now.More more;

    public class More {

        @SerializedName("txt")
        public String info;

    }


   /* public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_degree() {
        return city_degree;
    }

    public void setCity_degree(String city_degree) {
        this.city_degree = city_degree;
    }

    public String getCity_weather() {
        return city_weather;
    }

    public void setCity_weather(String city_weather) {
        this.city_weather = city_weather;
    }*/
}
