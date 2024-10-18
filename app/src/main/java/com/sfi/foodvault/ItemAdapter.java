package com.sfi.foodvault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    ArrayList<Integer> itemID;
    ArrayList<String> itemName, itemType, quantity, preferredStock, expirationDate;

    ItemAdapter( Context context, ArrayList<Integer> itemID, ArrayList<String> itemName, ArrayList<String> itemType, ArrayList<String> quantity,
                ArrayList<String> preferredStock, ArrayList<String> expirationDate) {
        this.context = context;
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.preferredStock = preferredStock;
        this.expirationDate = expirationDate;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.item_name_txt.setText(String.valueOf(itemName.get(position)));
        holder.item_type_txt.setText(String.valueOf(itemType.get(position)));
        holder.quantity_txt.setText(String.valueOf(quantity.get(position)));
        holder.stock_txt.setText(String.valueOf(preferredStock.get(position)));
        holder.exp_date_txt.setText(String.valueOf(expirationDate.get(position)));

        holder.addLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddDetails.class);
                intent.putExtra("userID", ((HomePage) context).userID);
                intent.putExtra("isUpdate", true);
                intent.putExtra("itemID", itemID.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemID.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_name_txt, item_type_txt, quantity_txt, stock_txt, exp_date_txt;
        LinearLayout addLayout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_type_txt = itemView.findViewById(R.id.item_type_txt);
            quantity_txt = itemView.findViewById(R.id.quantity_txt);
            stock_txt = itemView.findViewById(R.id.stock_txt);
            exp_date_txt = itemView.findViewById(R.id.exp_date_txt);
            addLayout = itemView.findViewById(R.id.addLayout);
        }
    }
}
