package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.udaily.databinding.ActivityAddTransactionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class add_transaction_activity extends AppCompatActivity {

    ActivityAddTransactionBinding binding;

    String type="";
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Spinner spinner;
    String[] l1={"Housing","Transportation","Hotelling","Personal Spending","Others"};
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        spinner = findViewById(R.id.dropdown_menu_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(add_transaction_activity.this, android.R.layout.simple_spinner_item,l1);
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

        binding.ExpenseCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Expense";
                binding.ExpenseCheckbox.setChecked(true);
                binding.IncomeCheckbox.setChecked(false);
            }
        });

        binding.IncomeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Income";
                binding.ExpenseCheckbox.setChecked(false);
                binding.IncomeCheckbox.setChecked(true);
            }
        });

        binding.addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = binding.userAmountAdd.getText().toString().trim();
                String note = binding.userNoteAdd.getText().toString().trim();
                if (amount.length() <= 0) {
                    return;
                }
                if (type.length()<=0){
                    Toast.makeText(add_transaction_activity.this, "Select Transaction Type !", Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy_HH:mm", Locale.getDefault());
                String currentdateandtime = sdf.format(new Date());

                String id = UUID.randomUUID().toString();
                Map<String, Object> transaction = new HashMap<>();
                transaction.put("Id", id);
                transaction.put("Amount", amount);
                transaction.put("Category", category);
                transaction.put("Note", note);
                transaction.put("Type", type);
                transaction.put("date", currentdateandtime);


                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Transactions").document(id)
                        .set(transaction)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(add_transaction_activity.this, "Transaction Added Succesfully !", Toast.LENGTH_SHORT).show();
                                binding.userNoteAdd.setText("");
                                binding.userAmountAdd.setText("");
                                binding.ExpenseCheckbox.setChecked(false);
                                binding.IncomeCheckbox.setChecked(false);

                                Intent intent = new Intent(add_transaction_activity.this,dashboard.class);
                                try {
                                    startActivity(intent);
                                }catch (Exception e){

                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(add_transaction_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}