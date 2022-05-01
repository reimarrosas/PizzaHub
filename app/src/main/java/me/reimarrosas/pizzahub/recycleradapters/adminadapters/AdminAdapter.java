package me.reimarrosas.pizzahub.recycleradapters.adminadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.AdminManageable;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.models.Extras;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.ItemCrudViewHolder;

public class AdminAdapter<T extends Extras> extends RecyclerView.Adapter<ItemCrudViewHolder<T>> implements Updatable<T>, AdminManageable {

    private final List<T> dataList = new ArrayList<>();
    private final Context context;
    private final Service<T> service;
    private final Notifiable n;
    private final MenuItem.MenuItemType type;

    public AdminAdapter(Context context, Service<T> service, Notifiable n, MenuItem.MenuItemType type) {
        this.context = context;
        this.service = service;
        this.n = n;
        this.type = type;
    }

    @NonNull
    @Override
    public ItemCrudViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.admin_item_row, parent, false);
        return new ItemCrudViewHolder<>(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCrudViewHolder<T> holder, int position) {
        T data = dataList.get(position);

        holder.setData(data);
        holder.setOnViewClick(view -> n.notifyUpdatedData(data, type));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyAddData(int position) {
        notifyItemInserted(position);
    }

    @Override
    public void notifyUpdateData(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void onViewSwiped(int position) {
        T data = dataList.get(position);
        dataList.remove(position);
        notifyItemRemoved(position);
        service.deleteData(data.getId());
    }

}
