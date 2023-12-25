package com.techwizards.wia2007_trash2treasure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001; //google

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

        //register with google
        CardView BtnRegisterGoogle= findViewById(R.id.BtnRegisterGoogle);

        BtnRegisterGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerWithGoogle();
            }
        });
    } //oncreate

    private void registerWithGoogle() {

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    } //register with google

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}

