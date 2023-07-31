package br.com.local.mobileled_wifi_v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextIP;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIP = findViewById(R.id.editTextIP);
        buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = editTextIP.getText().toString().trim();
                if (!ipAddress.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("IP_ADDRESS", ipAddress);
                    startActivity(intent);
                } else {
                    // Caso o usuário não insira um endereço IP válido, exiba um aviso
                    Toast.makeText(MainActivity.this, "Please enter the ESP32 IP address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}