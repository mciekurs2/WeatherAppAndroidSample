package lv.mciekurs.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView temp, pressure, humidity;
    EditText cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test button for calling http request
        Button searchButton = findViewById(R.id.search_button);
        cityText = findViewById(R.id.city_name_text);
        temp = findViewById(R.id.temp);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String newCityName = cityText.getText().toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + newCityName +
                        "&appid=API_KEY_HERE";
                GsonRequest<WeatherData> weatherDataRequest =
                        new GsonRequest<WeatherData>(url, WeatherData.class, null, onSuccess(), onFail());
                queue.add(weatherDataRequest);
                queue.start();
            }
        });

    }


    private Response.Listener<WeatherData> onSuccess() {
        return new Response.Listener<WeatherData>() {
            @Override
            public void onResponse(WeatherData res) {
                temp.setText(String.valueOf(res.main.temp));
                pressure.setText(String.valueOf(res.main.pressure));
                humidity.setText(String.valueOf(res.main.humidity));
            }
        };
    }

    private Response.ErrorListener onFail() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }


}