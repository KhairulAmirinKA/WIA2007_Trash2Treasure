package com.techwizards.wia2007_trash2treasure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText ETProfileEmail = findViewById(R.id.ETRegisterEmail);
        EditText ETProfilePassword = findViewById(R.id.ETRegisterPassword);
        EditText ETProfileRePassword = findViewById(R.id.ETRegisterRePassword);

        Button next = findViewById(R.id.BtnRegisterNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Patterns.EMAIL_ADDRESS.matcher(ETProfileEmail.getText().toString()).matches()){
                    if (ETProfilePassword.getText().toString().equals(ETProfileRePassword.getText().toString()) && ETProfilePassword.getText().length() >= 8) {
                        String email = ETProfileEmail.getText().toString();
                        String password = ETProfilePassword.getText().toString();

                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", password);

                        RegisterDetail registerDetail = new RegisterDetail();
                        registerDetail.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.FragmentRegisterMain, registerDetail)
                                .commit();
                    }
                }
            }
        });

        TextView registerToLogin = findViewById(R.id.BtnRegisterLogin);
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

