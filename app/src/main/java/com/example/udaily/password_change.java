package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.udaily.databinding.ActivityMainBinding;
import com.example.udaily.databinding.ActivityPasswordChangeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class password_change extends AppCompatActivity {

    ActivityPasswordChangeBinding binding;
    String email;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPasswordChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        firebaseAuth=FirebaseAuth.getInstance();

        binding.btnPasswordchangeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=binding.emailPasswordChange.getText().toString();
                if (email != null ){

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(password_change.this, "Check your Email for Link !", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(password_change.this,MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(password_change.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });

        binding.LoginLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(password_change.this,MainActivity.class));
                finish();
            }
        });
    }

    private void validatedata() {

    }
}