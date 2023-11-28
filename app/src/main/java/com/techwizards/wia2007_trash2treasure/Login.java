package com.techwizards.wia2007_trash2treasure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.BtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (performLogin()) {
                    saveLoginState();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        TextView loginToSignUp = findViewById(R.id.BtnLoginSignUp);
        loginToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }

    private boolean performLogin() {
        EditText ETEmail = findViewById(R.id.ETLoginEmail);
        EditText ETPassword = findViewById(R.id.ETLoginPassword);

        if (ETEmail.getText().toString().equals("admin@email.com")) {
            if (ETPassword.getText().toString().equals("admin123")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void saveLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }
}