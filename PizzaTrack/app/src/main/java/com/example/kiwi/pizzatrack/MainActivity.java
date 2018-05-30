package com.example.kiwi.pizzatrack;

import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button guardar;
    EditText idrepartidor;
    TextView locubicacion;
    GPSTracker gps;
    double lat;
    double longit;
    String idrepartidortexto="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        guardar = (Button) findViewById(R.id.button);
        locubicacion = (TextView) findViewById(R.id.locText);
        idrepartidor = (EditText) findViewById(R.id.idrepartidor);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idrepartidortexto = idrepartidor.getText().toString();
                gps = new GPSTracker(getApplicationContext());
                Location l = gps.getLocation();
                if(l!=null)
                {
                    lat = gps.getLatitude();
                    longit = gps.getLongitude();
                    locubicacion.setText("Su ubicacion cambio: "+lat+" ,"+longit);
                }
                Consumir con = new Consumir(getApplicationContext(),"http://puceing.edu.ec:15000/MartinMoran/REST/api/Location/"+idrepartidortexto,
                        idrepartidortexto,
                        String.valueOf(lat),
                       String.valueOf(longit));
                con.execute();
            }
        });
    }
}