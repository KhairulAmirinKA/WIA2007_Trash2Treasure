package com.techwizards.wia2007_trash2treasure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    GoogleSignInClient googleSignInClient;

    int RC_SIGN_IN = 20;

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

        //one tap sign in
        CardView BtnRegisterGoogle= findViewById(R.id.BtnRegisterGoogle);

        firebaseAuth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        GoogleSignInOptions gso= new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken( getString(R.string.default_web_id_client))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //on click
        BtnRegisterGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInUsingGoogle();
            }
        });










    } //oncreate

    private void signInUsingGoogle() {

        Intent intent = googleSignInClient.getSignInIntent();

        startActivityForResult(intent, RC_SIGN_IN);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuth(account.getIdToken() );
            }
            catch (Exception e){

                Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR SIGN IN", e.getMessage() );
            }
        }
    }

    private void firebaseAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();

                            map.put("id", user.getUid() );
                            map.put("name", user.getDisplayName() );
                            map.put("profile", user.getPhotoUrl().toString() );

                            database.getReference().child("users")
                                    .child(user.getUid())
                                    .setValue(map);

                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        } //if success

                        else {
                            Toast.makeText(Register.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    } //firebaseAuth



}
