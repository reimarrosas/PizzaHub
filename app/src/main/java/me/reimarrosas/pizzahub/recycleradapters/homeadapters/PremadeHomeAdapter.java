package me.reimarrosas.pizzahub.recycleradapters.homeadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.HomeViewHolder;

public class PremadeHomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final List<Premade> premadeList = new ArrayList<>();
    private Context context;

    public PremadeHomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Premade p = premadeList.get(position);

        holder.setItemName(p.getName());
        Glide.with(context)
                .asBitmap()
                .load(p.getImageUrl())
                .into(holder.getThumbNail());
    }

    @Override
    public int getItemCount() {
        return premadeList.size();
    }

    public void updateDataList(List<Premade> premadeList) {
        this.premadeList.clear();
        this.premadeList.addAll(premadeList);
        notifyDataSetChanged();
    }

}
