package br.com.local.mobileled_wifi_v4;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity2 extends AppCompatActivity {

    private Button buttonLedOn;
    private Button buttonLedOff;

    private String esp32IpAddress;

    private static final int ESP32_PORT = 80;
    private boolean isLedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        esp32IpAddress = getIntent().getStringExtra("IP_ADDRESS");

        buttonLedOn = findViewById(R.id.buttonLedOn);
        buttonLedOff = findViewById(R.id.buttonLedOff);

        updateButtonColors();

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

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendCommandToESP32(final String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket(esp32IpAddress, ESP32_PORT);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("GET " + command + " HTTP/1.1\r\n").getBytes());
                    outputStream.write("Host: esp32\r\n".getBytes());
                    outputStream.write("Connection: close\r\n".getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Atualiza o estado do LED
                    if (command.equals("/Led_On")) {
                        isLedOn = true;
                    } else if (command.equals("/Led_Off")) {
                        isLedOn = false;
                    }

                    // Atualiza as cores dos botões
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateButtonColors();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Connection to ESP32 failed.");
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void updateButtonColors() {
        if (isLedOn) {
            buttonLedOn.setBackgroundColor(Color.GREEN);
            buttonLedOff.setBackgroundColor(Color.GRAY);
        } else {
            buttonLedOn.setBackgroundColor(Color.GRAY);
            buttonLedOff.setBackgroundColor(Color.GREEN);
        }
    }
}