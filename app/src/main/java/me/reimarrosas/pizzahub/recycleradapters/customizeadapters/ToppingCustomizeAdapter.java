package me.reimarrosas.pizzahub.recycleradapters.customizeadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.CustomizePizzaData;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class ToppingCustomizeAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<CustomizePizzaData> {

    private final List<CustomizePizzaData> dataList = new ArrayList<>();

    private Context context;
    private Notifiable n;

    public ToppingCustomizeAdapter(Context context, Notifiable n) {
        this.context = context;
        this.n = n;
    }

    @Override
    public int getItemViewType(int position) {
        CustomizePizzaData cpd = dataList.get(position);
        return cpd.getDataType() == CustomizePizzaData.DataType.HEADER ? 0 : 1;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(
                        viewType == 0 ? R.layout.section_header : R.layout.item_row,
                        parent,
                        false);

        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        CustomizePizzaData cpd = dataList.get(position);

        switch (cpd.getDataType()) {
            case HEADER:
                break;
            case TOPPING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public List<CustomizePizzaData> getDataList() {
        return dataList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

}
