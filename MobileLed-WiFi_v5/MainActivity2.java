package br.com.local.mobileled_wifi_v5;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    public static final int ESP32_PORT = 80;

    private String esp32IpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        esp32IpAddress = getIntent().getStringExtra("IP_ADDRESS");

        Button buttonLed01 = findViewById(R.id.buttonLed01);
        Led01 ledControl = new Led01(buttonLed01, esp32IpAddress);

        Button buttonLed02 = findViewById(R.id.buttonLed02);
        Led02 ledControl02 = new Led02(buttonLed02, esp32IpAddress);

        Button buttonLed03 = findViewById(R.id.buttonLed03);
        Led03 ledControl03 = new Led03(buttonLed03, esp32IpAddress);

    }
}