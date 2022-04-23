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
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class DrinkComboAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<Drink> {

    private final List<Drink> drinkList = new ArrayList<>();
    private final List<Drink> selectedList = new ArrayList<>();

    private Context context;
    private Notifiable n;

    public DrinkComboAdapter(Context context, Notifiable n, List<Drink> selectedList) {
        this.context = context;
        this.n = n;
        this.selectedList.clear();
        this.selectedList.addAll(selectedList);
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
        Drink d = drinkList.get(position);

        holder.setName(d.getName());
        Glide.with(context)
                .asBitmap()
                .load(d.getImageUrl())
                .into(holder.getThumbNail());
        if(selectedList.contains(d)) {
            holder.updateDataCheckedState();
        }
        holder.addCheckListener(checkCardHandler(holder, d));
    }

    private View.OnClickListener checkCardHandler(DefaultViewHolder holder, Drink d) {
        return view -> {
            holder.updateDataCheckedState();
            n.notifyUpdatedData(d, MenuItem.MenuItemType.DRINK);
        };
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    @Override
    public List<Drink> getDataList() {
        return drinkList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

}
