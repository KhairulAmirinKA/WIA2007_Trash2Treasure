package com.techwizards.wia2007_trash2treasure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Login extends AppCompatActivity {

    DataManager dataManager = DataManager.getInstance();

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
                    dataManager.save(getApplicationContext());
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

        boolean status = false;

        boolean emailFound = false;
        boolean passwordCorrect = false;

        for (ProfileItem profileItem : dataManager.profileItems){
            if (ETEmail.getText().toString().equals(profileItem.email)) {
                emailFound = true;

                if (ETPassword.getText().toString().equals(profileItem.password)) {
                    passwordCorrect = true;
                    status = true;

                    ProfileItem loggedInUser = profileItem;
                    CurrentUser currentUser = new CurrentUser();
                    currentUser.setCurrentUser(loggedInUser);
                    dataManager.currentUser = currentUser;
                }
            }
        }

        if (!emailFound) {
            Toast.makeText(this, "Email not Registered!", Toast.LENGTH_SHORT).show();
        } else if (!passwordCorrect) {
            Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
        }

        return status;
    }

    private void saveLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }
}