package me.reimarrosas.pizzahub.recycleradapters.comboadapters;

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
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class ToppingComboAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<Topping> {

    private final List<Topping> toppingList = new ArrayList<>();

    private Context context;
    private Notifiable n;

    public ToppingComboAdapter(Context context, Notifiable n) {
        this.context = context;
        this.n = n;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);
        
        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        Topping t = toppingList.get(position);

        holder.setName(t.getName());
        Glide.with(context)
                .asBitmap()
                .load(t.getImageUrl())
                .into(holder.getThumbNail());
    }

    @Override
    public int getItemCount() {
        return toppingList.size();
    }

    @Override
    public List<Topping> getDataList() {
        return toppingList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

}
