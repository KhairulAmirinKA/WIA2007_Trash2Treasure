package com.techwizards.wia2007_trash2treasure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

public class Login extends AppCompatActivity {

    EditText ETEmail, ETPassword;
    DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETEmail = findViewById(R.id.ETLoginEmail);
        ETPassword = findViewById(R.id.ETLoginPassword);
        MaterialIconView showPassword = findViewById(R.id.BtnShowPassword);

        if (!ETPassword.getText().toString().isEmpty()) {
            showPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showPassword.isPressed()) {
                        showPassword.setIcon(MaterialDrawableBuilder.IconValue.EYE_OFF);
                        ETPassword.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        showPassword.setIcon(MaterialDrawableBuilder.IconValue.EYE);
                        ETPassword.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }
            });
        }

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
        boolean status = false;

        boolean emailFound = false;
        boolean passwordCorrect = false;

        for (ProfileItem profileItem : dataManager.profileItems){
            if (ETEmail.getText().toString().equals(profileItem.email)) {
                emailFound = true;

                String enteredPasswordHash = profileItem.hashPassword(ETPassword.getText().toString());

                System.out.println("Entered Hash: " + enteredPasswordHash);
                System.out.println("Firebase Hash: " + profileItem.getPasswords().get("hashed"));
                if (enteredPasswordHash.equals(profileItem.getPasswords().get("hashed"))) {
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