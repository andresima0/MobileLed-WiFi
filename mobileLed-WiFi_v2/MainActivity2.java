package br.com.local.mobileled_wifi_v2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity2 extends AppCompatActivity {

    private Button buttonLedOn;
    private Button buttonLedOff;

    private String esp32IpAddress;

    private static final int ESP32_PORT = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        esp32IpAddress = getIntent().getStringExtra("IP_ADDRESS");

        buttonLedOn = findViewById(R.id.buttonLedOn);
        buttonLedOff = findViewById(R.id.buttonLedOff);

        buttonLedOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToESP32("/Led_On");
            }
        });

        buttonLedOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToESP32("/Led_Off");
            }
        });
    }

    private void sendCommandToESP32(final String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(esp32IpAddress, ESP32_PORT);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("GET " + command + " HTTP/1.1\r\n").getBytes());
                    outputStream.write("Host: esp32\r\n".getBytes());
                    outputStream.write("Connection: close\r\n".getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                    outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}