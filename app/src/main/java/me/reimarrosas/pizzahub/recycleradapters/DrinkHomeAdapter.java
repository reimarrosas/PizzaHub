package me.reimarrosas.pizzahub.recycleradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.HomeViewHolder;

public class DrinkHomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final List<Drink> drinkList = new ArrayList<>();
    private Context context;

    public DrinkHomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Drink d = drinkList.get(position);

        holder.setItemName(d.getName());
        Glide.with(context)
                .asBitmap()
                .load(d.getImageUrl())
                .into(holder.getThumbNail());
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public void updateDataList(List<Drink> drinkList) {
        this.drinkList.clear();
        this.drinkList.addAll(drinkList);
        notifyDataSetChanged();
    }

}
