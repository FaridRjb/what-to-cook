package com.faridrjb.whattocook.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faridrjb.whattocook.R;

import java.util.ArrayList;

public class InitsNeededRVAdapter extends RecyclerView.Adapter<InitsNeededRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> initsNames = new ArrayList<>();
    private ArrayList<String> initsAmount = new ArrayList<>();

    public InitsNeededRVAdapter(Context context, ArrayList<String> initsNames, ArrayList<String> initsAmount) {
        this.context = context;
        this.initsNames = initsNames;
        this.initsAmount = initsAmount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inits, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.initName.setText(initsNames.get(position));
        holder.initAmount.setText(initsAmount.get(position));
    }

    @Override
    public int getItemCount() {
        return initsNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView initName, initAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layoutInit);
            initName = itemView.findViewById(R.id.txtInit);
            initAmount = itemView.findViewById(R.id.txtAmount);
        }
    }

}
