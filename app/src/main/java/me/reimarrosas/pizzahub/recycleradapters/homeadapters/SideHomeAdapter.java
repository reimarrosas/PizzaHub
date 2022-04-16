package me.reimarrosas.pizzahub.recycleradapters.homeadapters;

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
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.HomeViewHolder;

public class SideHomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final List<Side> sideList = new ArrayList<>();
    private Context context;

    public SideHomeAdapter(Context context) {
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
        Side s = sideList.get(position);

        holder.setItemName(s.getName());
        Glide.with(context)
                .asBitmap()
                .load(s.getImageUrl())
                .into(holder.getThumbNail());
    }

    @Override
    public int getItemCount() {
        return sideList.size();
    }

    public void updateDataList(List<Side> sideList) {
        this.sideList.clear();
        this.sideList.addAll(sideList);
        notifyDataSetChanged();
    }

}
