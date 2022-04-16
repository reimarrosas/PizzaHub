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
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class SideComboAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<Side> {

    private final List<Side> sideList = new ArrayList<>();

    private Context context;
    private Notifiable n;

    public SideComboAdapter(Context context, Notifiable n) {
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
        Side s = sideList.get(position);

        holder.setName(s.getName());
        Glide.with(context)
                .asBitmap()
                .load(s.getImageUrl())
                .into(holder.getThumbNail());
        holder.addCheckListener(checkCardHandler(holder, s));
    }

    private View.OnClickListener checkCardHandler(DefaultViewHolder holder, Side s) {
        return view -> {
            holder.updateDataCheckedState();
            n.notifyUpdatedData(s, MenuItem.MenuItemType.SIDE);
        };
    }

    @Override
    public int getItemCount() {
        return sideList.size();
    }

    @Override
    public List<Side> getDataList() {
        return sideList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

}
