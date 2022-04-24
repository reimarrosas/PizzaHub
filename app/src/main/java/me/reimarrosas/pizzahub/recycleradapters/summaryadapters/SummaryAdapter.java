package me.reimarrosas.pizzahub.recycleradapters.summaryadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderSummaryData;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderSummaryData.DataType;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.SectionHeaderViewHolder;

public class SummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<OrderSummaryData> dataList;

    private Context context;

    public SummaryAdapter(Context context, List<OrderSummaryData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType().ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;
        RecyclerView.ViewHolder holder;

        if (viewType == DataType.SECTION_HEADER.ordinal()) {
            view = inflater.inflate(R.layout.section_header, parent, false);
            holder = new SectionHeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_row, parent, false);
            holder = new DefaultViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderSummaryData osd = dataList.get(position);

        switch(osd.getType()) {
            case SECTION_HEADER:
                SectionHeaderViewHolder sectionHeader = (SectionHeaderViewHolder) holder;
                sectionHeader.setSectionName(osd.getTitle());
                break;
            default:
                DefaultViewHolder defaultHolder = (DefaultViewHolder) holder;
                defaultHolder.setName(osd.getData().getName());
                Glide.with(context)
                        .asBitmap()
                        .load(osd.getData().getImageUrl())
                        .into(defaultHolder.getThumbNail());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
