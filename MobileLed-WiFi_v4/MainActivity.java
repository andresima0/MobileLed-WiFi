package br.com.local.mobileled_wifi_v4;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
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

        // Adiciona o TextWatcher para formatar o número de IP enquanto o usuário digita
        editTextIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String userInput = editable.toString().trim().replace(".", "");

                StringBuilder formattedStringBuilder = new StringBuilder();
                int[] segmentLengths = {3, 3, 1, 2};
                int userInputIndex = 0;

                for (int segmentLength : segmentLengths) {
                    if (userInputIndex + segmentLength > userInput.length()) {
                        segmentLength = userInput.length() - userInputIndex;
                    }
                    String segment = userInput.substring(userInputIndex, userInputIndex + segmentLength);
                    formattedStringBuilder.append(segment);
                    userInputIndex += segmentLength;
                    if (userInputIndex < userInput.length()) {
                        formattedStringBuilder.append(".");
                    }
                }

                editTextIP.removeTextChangedListener(this);
                editTextIP.setText(formattedStringBuilder.toString());
                editTextIP.setSelection(formattedStringBuilder.length());
                editTextIP.addTextChangedListener(this);
            }
        });

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