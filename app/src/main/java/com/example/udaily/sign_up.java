package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.model.LottieCompositionCache;
import com.example.udaily.databinding.ActivityMainBinding;
import com.example.udaily.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();

        getSupportActionBar().hide();

     /*   binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailSignup.getText().toString();
                String password=binding.passwordSignup.getText().toString();
                if(email.trim().length()<=0 || password.trim().length()<=0){
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(sign_up.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
                    }
                })
            }
        });*/

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailSignup.getText().toString();
                String password = binding.passwordSignup.getText().toString();
                if (email.trim().length() <= 0 || password.trim().length() <= 0) {
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(sign_up.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(sign_up.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_up.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.LoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(sign_up.this,MainActivity.class);
                try {
                    startActivity(intent2);
                }catch (Exception e){

                }
            }
        });
    }
}