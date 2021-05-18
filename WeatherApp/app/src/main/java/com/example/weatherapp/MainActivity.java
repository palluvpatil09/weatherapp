package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ImageView imageView;
    TextView temptv, time, country, city_nam, max_temp, min_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        temptv = findViewById(R.id.textView3);
        time = findViewById(R.id.textView2);

        country = findViewById(R.id.country);
        city_nam = findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FindWeather();
            }
        });

        }
        public void FindWeather()
        {
            final String city = editText.getText().toString();
           String url ="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("main");
                        double temp = object.getDouble("temp");
                        temptv.setText("Temperature\n"+temp+"°C");

                        JSONObject object8 = jsonObject.getJSONObject("sys");
                        String count = object8.getString("country");
                        country.setText(count+"  :");

                        String city = jsonObject.getString("name");
                        city_nam.setText(city);

                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject obj = jsonArray.getJSONObject(0);
                        String icon = obj.getString("icon");
                        Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                        String date = std.format(calendar.getTime());
                        time.setText(date);

                        JSONObject object10 = jsonObject.getJSONObject("main");
                        double mintemp = object10.getDouble("temp_min");
                        min_temp.setText("Min Temp\n"+mintemp+" °C");

                        JSONObject object12 = jsonObject.getJSONObject("main");
                        double maxtemp = object12.getDouble("temp_max");
                        max_temp.setText("Max Temp\n"+maxtemp+" °C");

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
    }

}