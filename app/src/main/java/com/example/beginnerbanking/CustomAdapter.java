package com.example.beginnerbanking;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Delete;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    CustomerDao dao;
    private List<Customer> customerList;


    public CustomAdapter(List<Customer> customerList, CustomerDao dao) {
        this.customerList = customerList;
        this.dao = dao;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, balanceText,edit_deposit;
        ImageButton deletebutton,addmoneybutton,update_balance,update_cancel,yes,no;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            balanceText = itemView.findViewById(R.id.balanceText);
            deletebutton=itemView.findViewById(R.id.deletebutton);
            addmoneybutton=itemView.findViewById(R.id.addmoneybutton);
            update_balance=itemView.findViewById(R.id.update_balance);
            update_cancel=itemView.findViewById(R.id.update_cancel);
            edit_deposit=itemView.findViewById(R.id.edit_deposit);
            yes=itemView.findViewById(R.id.yes);
            no=itemView.findViewById(R.id.no);
        }
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.nameText.setText(customer.Name);
        holder.balanceText.setText("₹ " + customer.Balance);
        holder.deletebutton.setOnClickListener(v -> {
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.confirmdelete, null);
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setView(view)
                            .create();
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                    view.setAlpha(0f);
                    view.setScaleX(0.8f);
                    view.setScaleY(0.8f);
                    view.animate()
                            .alpha(1f)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(300)
                            .start();
            Button yes = view.findViewById(R.id.yes);
            Button no = view.findViewById(R.id.no);

            yes.setOnClickListener(Z -> {
                dao.delete(customer);
                customerList.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                dialog.dismiss();


            });

            no.setOnClickListener(Z -> dialog.dismiss());

            dialog.show();
                    });



        holder.addmoneybutton.setOnClickListener(V -> {
            View view = LayoutInflater.from(V.getContext()).inflate(R.layout.admindeposit, null);
            AlertDialog dialog = new AlertDialog.Builder(V.getContext())
                    .setView(view)
                    .create();
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            view.setAlpha(0f);
            view.setScaleX(0.8f);
            view.setScaleY(0.8f);
            view.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start();

            EditText edit_deposit = view.findViewById(R.id.edit_deposit);
            Button update_balance = view.findViewById(R.id.update_balance);
            Button update_cancel = view.findViewById(R.id.update_cancel);

            update_balance.setOnClickListener(v -> {
                String newBalance= String.valueOf(edit_deposit.getText());

                customer.Balance+=Integer.parseInt(newBalance);
                dialog.dismiss();


            });

            update_cancel.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

}
