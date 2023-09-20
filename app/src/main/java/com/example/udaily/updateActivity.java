package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.udaily.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class updateActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    Spinner spinner;
    String[] l1={"Housing","Transportation","Hotelling","Personal Spending","Others"};
    String category,newType;

    ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        String Id= getIntent().getStringExtra("Id");
        String Amount= getIntent().getStringExtra("Amount");
        String Note= getIntent().getStringExtra("Note");
        String Type= getIntent().getStringExtra("Type");
        //String category= getIntent().getStringExtra("Category");

        binding.userAmountAdd.setText(Amount);
        binding.userNoteAdd.setText(Note);

        switch (Type){
            case "Income":
                newType="Income";
                binding.IncomeCheckbox.setChecked(true);
                binding.ExpenseCheckbox.setChecked(false);
                break;

            case "Expense":
                newType="Expense";
                binding.IncomeCheckbox.setChecked(false);
                binding.ExpenseCheckbox.setChecked(true);
                break;
        }

        binding.IncomeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newType="Income";
                binding.IncomeCheckbox.setChecked(true);
                binding.ExpenseCheckbox.setChecked(false);
            }
        });

        binding.ExpenseCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newType="Expense";
                binding.IncomeCheckbox.setChecked(false);
                binding.ExpenseCheckbox.setChecked(true);
            }
        });

        spinner = findViewById(R.id.dropdown_menu_category2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(updateActivity.this, android.R.layout.simple_spinner_item,l1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(updateActivity.this);
                builder.setTitle("Alert !")
                        .setMessage("Are You Sure You Want To Update This Transaction ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String Amount= binding.userAmountAdd.getText().toString();
                                String Note= binding.userNoteAdd.getText().toString();
                                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                                        .collection("Transactions").document(Id)
                                        .update("Amount",Amount,"Note",Note,"Type",newType,"Category",category)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(updateActivity.this, "Update Successful !", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(updateActivity.this,dashboard.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(updateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                       AlertDialog dialog = builder.create();
                       dialog.show();


            }
        });

        binding.btnDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(updateActivity.this);
                builder.setTitle("Alert !")
                        .setMessage("Are You Sure You Want To Delete This Transaction ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                                        .collection("Transactions")
                                        .document(Id).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(updateActivity.this, "Delete Successful !", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(updateActivity.this,dashboard.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(updateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



    }
}