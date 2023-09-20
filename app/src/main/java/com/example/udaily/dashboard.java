package com.example.udaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaily.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    TextView email,email1;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    int sumExpense=0;
    int sumIncome=0;
   // int pass_housing=0,pass_transportation=0,pass_hoteling=0,pass_personalsepnding=0,pass_others=0;
   // String housing,transportation,hoteling,personalsepnding,others;
    //Context context;
   // Context context;

    ArrayList<TransactionModel> transactionModelArrayList;
    TransactionAdapter transactionAdapter;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        transactionModelArrayList=new ArrayList<>();

        email1=findViewById(R.id.user_email_dashboard);
        user= FirebaseAuth.getInstance().getCurrentUser();
        email1.setText(user.getEmail());

        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRecyclerView.setHasFixedSize(true);

        MaterialToolbar toolbar = findViewById(R.id.dashboard_toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try {
                 drawerLayout.openDrawer(GravityCompat.START);
                 email=findViewById(R.id.nav_user_email);


                 email.setText(user.getEmail());

             }
             catch (Exception e){

             }
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               try {
                   int id = item.getItemId();
                   drawerLayout.closeDrawer(GravityCompat.START);
                   switch (id)
                   {
                       case R.id.nav_home:
                           break;

                       case R.id.nav_analysis:
                           AlertDialog.Builder builder=new AlertDialog.Builder(dashboard.this);
                           builder.setTitle("Alert !")
                                   .setMessage("This Feature is Coming Soon !")
                                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                       }
                                   });

                           AlertDialog dialog = builder.create();
                           dialog.show();
                          // startActivity(new Intent(dashboard.this,transaction_analysis.class));
                           break;

                       case R.id.nav_change_password:
                           //Toast.makeText(dashboard.this , "Change Password clicked",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(dashboard.this,password_change.class));
                           break;

                       case R.id.nav_log_out:
                           // Toast.makeText(dashboard.this , "Log Out clicked",Toast.LENGTH_SHORT).show();
                           //FirebaseAuth.getInstance().signOut();
                           AlertDialog.Builder builder1=new AlertDialog.Builder(dashboard.this);
                           builder1.setTitle("Alert !")
                                   .setMessage("Are You Sure You Want To Log Out ?")
                                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                           firebaseAuth.signOut();
                                           startActivity(new Intent(dashboard.this,MainActivity.class));
                                           finish();

                                       }
                                   })
                                   .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                       }
                                   });

                           AlertDialog dialog1 = builder1.create();
                           dialog1.show();

                           break;

                       case R.id.nav_about:
                          // Toast.makeText(dashboard.this , "About clicked",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(dashboard.this,about_us.class));
                           break;

                       default:
                           return true;
                   }
               }
               catch (Exception e) {

               }
                return true;
            }
        });

        binding.floatingBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(dashboard.this,add_transaction_activity.class));
                }catch (Exception e){

                }
            }
        });
        try {
            loadData();
        }catch (Exception e){}

    }

    private void loadData() {
        firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Transactions")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot ds:task.getResult()){
                            TransactionModel model=new TransactionModel(
                                    ds.getString("Amount"),
                                    ds.getString("Category"),
                                    ds.getString("Id"),
                                    ds.getString("Note"),
                                    ds.getString("Type"),
                                    ds.getString("date"));

                           /* int amount=Integer.parseInt(ds.getString("Amount"));
                            if(ds.getString("Type").equals("Income")){
                                sumIncome=sumIncome+amount;
                                if(ds.getString("Category").equals("Housing")){
                                    //String pass_housing,pass_transportation,pass_hoteling,pass_personalsepnding,pass_others;
                                    pass_housing=pass_housing+amount;


                                } else if (ds.getString("Category").equals("Transportation")) {
                                    pass_transportation=pass_transportation+amount;

                                } else if (ds.getString("Category").equals("Hoteling")) {
                                    pass_hoteling=pass_hoteling+amount;

                                } else if (ds.getString("Category").equals("Personal Sepnding")) {
                                    pass_personalsepnding=pass_personalsepnding+amount;

                                }else {
                                    pass_others=pass_others+amount;

                                }
                            }
                            else {
                                sumExpense=sumExpense+amount;
                                if(ds.getString("Category").equals("Housing")){
                                    //String pass_housing,pass_transportation,pass_hoteling,pass_personalsepnding,pass_others;
                                    pass_housing=pass_housing+amount;


                                } else if (ds.getString("Category").equals("Transportation")) {
                                    pass_transportation=pass_transportation+amount;

                                } else if (ds.getString("Category").equals("Hoteling")) {
                                    pass_hoteling=pass_hoteling+amount;

                                } else if (ds.getString("Category").equals("Personal Sepnding")) {
                                    pass_personalsepnding=pass_personalsepnding+amount;

                                }else {
                                    pass_others=pass_others+amount;

                                }
                            }*/


                            transactionModelArrayList.add(model);

                            // passing data to analysis for pie chart
                          /*  housing=String.valueOf(pass_housing);
                            transportation=String.valueOf(pass_transportation);
                            hoteling=String.valueOf(pass_hoteling);
                            personalsepnding=String.valueOf(pass_personalsepnding);
                            others=String.valueOf(pass_others);*/


                        }

                       /* Intent intent1 = new Intent(dashboard.this,transaction_analysis.class);

                        intent1.putExtra("pass_housing",housing);
                        intent1.putExtra("pass_transportation",transportation);
                        intent1.putExtra("pass_hoteling",hoteling);
                        intent1.putExtra("pass_personalsepnding",personalsepnding);
                        intent1.putExtra("pass_others",others);

                        try{
                            startActivity(intent1);

                        }catch (Exception e){}*/

                        binding.totalIncome.setText(String.valueOf(sumIncome));
                        binding.totalExpense.setText(String.valueOf(sumExpense));
                        binding.totalBalance.setText(String.valueOf((sumIncome-sumExpense)));

                        transactionAdapter=new TransactionAdapter(dashboard.this,transactionModelArrayList);
                        binding.historyRecyclerView.setAdapter(transactionAdapter);
                    }
                });





    }

}