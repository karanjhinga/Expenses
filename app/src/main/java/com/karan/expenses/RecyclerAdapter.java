package com.karan.expenses;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private List<Record> list;
    Context context;
    myInterface myInterface;

    public RecyclerAdapter(List<Record> list, myInterface myInterface) {
        this.list = list;
        this.context = context;
        this.myInterface = myInterface;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Record record = list.get(position);
        holder.cat.setText(record.getCategory());
        holder.val.setText(String.valueOf(record.getValue()));
        holder.dis.setText(record.getDescription());
        if (record.getType().equals(DatabaseHelper.TYPE_INCOME)) {
            holder.val.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
        } else if (record.getType().equals(DatabaseHelper.TYPE_EXPENSE)) {
            holder.val.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView val, cat, dis;

        public Holder(View itemView) {
            super(itemView);
            val = itemView.findViewById(R.id.value);
            cat = itemView.findViewById(R.id.category);
            dis = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myInterface.getposition(getAdapterPosition(),v);
                }
            });
        }
    }
}