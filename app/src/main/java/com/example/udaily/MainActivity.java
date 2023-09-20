package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.model.LottieCompositionCache;
import com.example.udaily.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


   /* @Override
    protected void onStart() {
        super.onStart();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            startActivity(new Intent(MainActivity.this,dashboard.class));
        }else {
           // startActivity(new Intent(MainActivity.this,dashboard.class));
        }
    }*/

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    try{
                        startActivity(new Intent(MainActivity.this,dashboard.class));
                    }catch (Exception e){

                    }
                }
            }
        });

        getSupportActionBar().hide();



        binding.SignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,sign_up.class);
                try {

                    startActivity(intent1);
                }catch (Exception e){

                }
            }
        });


        binding.forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,password_change.class));

            }
        });



        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailLogin.getText().toString().trim();
                String password = binding.passwordLogin.getText().toString().trim();
                if (email.length() <= 0 || password.length() <= 0) {
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                try {
                                    Toast.makeText(MainActivity.this,"Log in Successful !",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this , dashboard.class));
                                }
                                catch(Exception e){

                                }
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}