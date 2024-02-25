package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class analysis_Newactivity extends AppCompatActivity {

    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    TextView housingPercentage, transportationPercentage, hotellingPercentage, personalSpendingPercentage, othersPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_newactivity);

        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        housingPercentage = findViewById(R.id.housing_percentage);
        transportationPercentage = findViewById(R.id.transportation_percentage);
        hotellingPercentage = findViewById(R.id.hotelling_percentage);
        personalSpendingPercentage = findViewById(R.id.personal_spending_percentage);
        othersPercentage = findViewById(R.id.others_percentage);

        fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Transactions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map<String, Double> categoryAmountMap = new HashMap<>();
                        double totalAmount = 0.0;

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String category = documentSnapshot.getString("Category");
                            double amount = Double.parseDouble(documentSnapshot.getString("Amount"));

                            totalAmount += amount;
                            if (categoryAmountMap.containsKey(category)) {
                                categoryAmountMap.put(category, categoryAmountMap.get(category) + amount);
                            } else {
                                categoryAmountMap.put(category, amount);
                            }
                        }

                        for (Map.Entry<String, Double> entry : categoryAmountMap.entrySet()) {
                            String category = entry.getKey();
                            double amount = entry.getValue();
                            double percentage = (amount / totalAmount) * 100;

                            switch (category) {
                                case "Housing":
                                    housingPercentage.setText(String.format("%.2f", percentage) + "%");
                                    break;
                                case "Transportation":
                                    transportationPercentage.setText(String.format("%.2f", percentage) + "%");
                                    break;
                                case "Hotelling":
                                    hotellingPercentage.setText(String.format("%.2f", percentage) + "%");
                                    break;
                                case "Personal Spending":
                                    personalSpendingPercentage.setText(String.format("%.2f", percentage) + "%");
                                    break;
                                case "Others":
                                    othersPercentage.setText(String.format("%.2f", percentage) + "%");
                                    break;
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(analysis_Newactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
/*

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.udaily.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class analysis_Newactivity extends AppCompatActivity {

    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_newactivity);

        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        pieChart = findViewById(R.id.pie_chart);

        fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Transactions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map<String, Double> categoryAmountMap = new HashMap<>();
                        double totalAmount = 0.0;

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String category = documentSnapshot.getString("Category");
                            double amount = Double.parseDouble(documentSnapshot.getString("Amount"));

                            totalAmount += amount;
                            if (categoryAmountMap.containsKey(category)) {
                                categoryAmountMap.put(category, categoryAmountMap.get(category) + amount);
                            } else {
                                categoryAmountMap.put(category, amount);
                            }
                        }

                        ArrayList<PieEntry> entries = new ArrayList<>();
                        for (Map.Entry<String, Double> entry : categoryAmountMap.entrySet()) {
                            String category = entry.getKey();
                            double amount = entry.getValue();
                            double percentage = (amount / totalAmount) * 100;
                            entries.add(new PieEntry((float) percentage, category));
                        }

                        PieDataSet dataSet = new PieDataSet(entries, "Category-wise Percentages");
                        dataSet.setColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA);
                        dataSet.setValueTextSize(12f);

                        PieData pieData = new PieData(dataSet);
                        pieChart.setData(pieData);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.invalidate(); // refresh
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(analysis_Newactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
*/

