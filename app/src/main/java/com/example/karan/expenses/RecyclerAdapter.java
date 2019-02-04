package com.example.karan.expenses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private List<SingleRecord> list;
    Context context;
    myInterface myInterface;

    public RecyclerAdapter(List<SingleRecord> list,myInterface myInterface) {
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
        SingleRecord singleRecord = list.get(position);
        holder.cat.setText(singleRecord.getCategory());
        holder.val.setText(singleRecord.getValue().toString());
        holder.dis.setText(singleRecord.getDiscription());
        if (singleRecord.getTYPE().equals(DatabaseHelper.TYPE_INCOME)) {
            holder.val.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
        } else if (singleRecord.getTYPE().equals(DatabaseHelper.TYPE_EXPENSE)) {
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
            dis = itemView.findViewById(R.id.discription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myInterface.getposition(getAdapterPosition(),v);
                }
            });
        }
    }
}