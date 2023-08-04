package br.com.local.mobileled_wifi_v5;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Led03 {

    private Button buttonLed03;
    private String esp32IpAddress;
    private boolean isLedOn = false;

    public Led03(Button buttonLed03, String esp32IpAddress) {
        this.buttonLed03 = buttonLed03;
        this.esp32IpAddress = esp32IpAddress;

        updateButtonColor();

        buttonLed03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLed();
            }
        });
    }

    private void toggleLed() {
        sendCommandToESP32(isLedOn ? "/Led03_Off" : "/Led03_On");
    }

    private void sendCommandToESP32(final String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket(esp32IpAddress, MainActivity2.ESP32_PORT);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("GET " + command + " HTTP/1.1\r\nHost: esp32\r\nConnection: close\r\n\r\n").getBytes());
                    outputStream.flush();
                    outputStream.close();
                    isLedOn = command.equals("/Led03_On");
                    updateButtonColor();
                } catch (IOException e) {
                    e.printStackTrace();
                    showConnectionFailedToast();
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

    private void updateButtonColor() {
        buttonLed03.post(new Runnable() {
            @Override
            public void run() {
                buttonLed03.setBackgroundColor(isLedOn ? Color.GREEN : Color.GRAY);
            }
        });
    }

    private void showConnectionFailedToast() {
        buttonLed03.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(buttonLed03.getContext(), "Connection to ESP32 failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}