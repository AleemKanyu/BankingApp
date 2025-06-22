package com.example.beginnerbanking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Delete;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    CustomerDao dao;
    private List<Customer> customerList;


    public CustomAdapter(List<Customer> customerList, CustomerDao dao) {
        this.customerList = customerList;
        this.dao = dao;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, balanceText;
        ImageButton deletebutton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            balanceText = itemView.findViewById(R.id.balanceText);
            deletebutton=itemView.findViewById(R.id.deletebutton);

        }
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.nameText.setText(customer.Name);
        holder.balanceText.setText("₹ " + customer.Balance);
        holder.deletebutton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this customer?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dao.delete(customer);
                        customerList.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

}
