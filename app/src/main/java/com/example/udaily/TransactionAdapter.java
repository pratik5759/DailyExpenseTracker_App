package com.example.udaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransactionModel> transactionModelArrayList;
    //String pass_housing,pass_transportation,pass_hoteling,pass_personalsepnding,pass_others;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactionModelArrayList) {
        this.context = context;
        this.transactionModelArrayList = transactionModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);

        return new MyViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TransactionModel model=transactionModelArrayList.get(position);
        String priority=model.getType();
        if(priority.equals("Expense")){
          holder.priority.setBackgroundResource(R.drawable.red_shape);
            holder.amount.setTextColor(context.getResources().getColor(R.color.red));
        }
        else {
            holder.priority.setBackgroundResource(R.drawable.green_shape);
            holder.amount.setTextColor(context.getResources().getColor(R.color.green));
        }

        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        holder.note.setText(model.getNote());
        holder.category.setText(model.getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,updateActivity.class);
                intent.putExtra("Id",transactionModelArrayList.get(position).getId());
                intent.putExtra("Amount",transactionModelArrayList.get(position).getAmount());
                intent.putExtra("Note",transactionModelArrayList.get(position).getNote());
                intent.putExtra("Type",transactionModelArrayList.get(position).getType());
                intent.putExtra("Category",transactionModelArrayList.get(position).getCategory());
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return transactionModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView note,amount,date,category;
        View priority;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.ct_one);
            note=itemView.findViewById(R.id.note_one);
            amount=itemView.findViewById(R.id.amount_one);
            date=itemView.findViewById(R.id.date_one);
            priority=itemView.findViewById(R.id.priority_one);
        }
    }
}
