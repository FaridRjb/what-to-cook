package com.faridrjb.whattocook.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faridrjb.whattocook.Food;
import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.activities.FoodDescActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class PossibleRVAdapter extends RecyclerView.Adapter<PossibleRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Food> foods = new ArrayList<>();

    public PossibleRVAdapter(Context context, ArrayList<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.foodName.setText(foods.get(position).getFoodName());
        holder.foodImg.setImageResource(context.getResources().getIdentifier("drawable/"+ foods.get(position).getPhoto(), null, context.getPackageName()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.parentLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
                Intent intent = new Intent(context, FoodDescActivity.class);
                intent.putExtra("name", foods.get(position).getFoodName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView foodName;
        RoundedImageView foodImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layoutFoodItem);
            foodName = itemView.findViewById(R.id.txtFood);
            foodImg = itemView.findViewById(R.id.imgFood);
        }
    }
}
