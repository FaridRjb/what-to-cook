package com.faridrjb.whattocook.recyclerviewadapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faridrjb.whattocook.R;

import java.util.ArrayList;

public class StorageItemsAdapter extends RecyclerView.Adapter<StorageItemsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> itemList = new ArrayList<>();

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public StorageItemsAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;

        preferences = context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_storage, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.checkBox.setText(itemList.get(position));
        holder.checkBox.setChecked(preferences.getBoolean(itemList.get(position), false));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Shit", "onClick");
                editor.putBoolean(itemList.get(position), ! preferences.getBoolean(holder.checkBox.getText().toString(), false));
                editor.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
