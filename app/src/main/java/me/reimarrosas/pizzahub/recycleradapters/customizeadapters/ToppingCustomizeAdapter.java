package me.reimarrosas.pizzahub.recycleradapters.customizeadapters;

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
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.CustomizePizzaData;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.SectionHeaderViewHolder;

public class ToppingCustomizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Updatable<CustomizePizzaData> {

    private final List<CustomizePizzaData> dataList = new ArrayList<>();
    private final List<Topping> selectedList;

    private Context context;
    private Notifiable n;

    public ToppingCustomizeAdapter(Context context, Notifiable n, List<Topping> selectedList) {
        this.context = context;
        this.n = n;
        this.selectedList = selectedList;
    }

    @Override
    public int getItemViewType(int position) {
        CustomizePizzaData cpd = dataList.get(position);
        return cpd.getDataType() == CustomizePizzaData.DataType.HEADER ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == 0 ? R.layout.section_header : R.layout.item_row;
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        if (viewType == 0) {
            return new SectionHeaderViewHolder(view);
        }

        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomizePizzaData cpd = dataList.get(position);

        switch (cpd.getDataType()) {
            case HEADER:
                SectionHeaderViewHolder sectionViewHolder = (SectionHeaderViewHolder) holder;
                sectionViewHolder.setSectionName(cpd.getSectionName());
                break;
            case TOPPING:
                DefaultViewHolder defaultViewHolder = (DefaultViewHolder) holder;
                defaultViewHolder.setName(cpd.getTopping().getName());
                Glide.with(context)
                        .asBitmap()
                        .load(cpd.getTopping().getImageUrl())
                        .into(defaultViewHolder.getThumbNail());
                defaultViewHolder.addCheckListener(checkCardHandler(defaultViewHolder, cpd));
                defaultViewHolder.setChecked(selectedList.contains(cpd.getTopping()));
                break;
        }
    }

    private View.OnClickListener checkCardHandler(DefaultViewHolder defaultViewHolder, CustomizePizzaData cpd) {
        return view -> {
            Log.d("ToppingCustomize", "checkCardHandler: " + cpd.getTopping());
            defaultViewHolder.updateDataCheckedState();
            n.notifyUpdatedData(cpd.getTopping(), MenuItem.MenuItemType.TOPPING);
        };
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
