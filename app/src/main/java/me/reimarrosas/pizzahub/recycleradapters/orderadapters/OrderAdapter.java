package me.reimarrosas.pizzahub.recycleradapters.orderadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.fragments.base.OrderHistoryFragmentDirections;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Pair;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderData;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderData.DataType;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.OrderContentViewHolder;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.OrderFooterViewHolder;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.OrderHeaderViewHolder;

public abstract class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Updatable<OrderData> {

    private final List<OrderData> dataList = new ArrayList<>();

    private Context context;

    public OrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType().ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == OrderData.DataType.HEADER.ordinal()) {
            return new OrderHeaderViewHolder(
                    LayoutInflater.from(context)
                            .inflate(R.layout.order_header, parent, false));
        } else if (viewType == OrderData.DataType.CONTENT.ordinal()) {
            return new OrderContentViewHolder(
                    LayoutInflater.from(context)
                            .inflate(R.layout.order_content, parent, false));
        }
        return new OrderFooterViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.order_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderData data = dataList.get(position);

        switch (data.getType()) {
            case HEADER:
                OrderHeaderViewHolder headerHolder = (OrderHeaderViewHolder) holder;
                headerHolder.setHeaderData(data.getHeaderData());
                break;
            case CONTENT:
                OrderContentViewHolder contentHolder = (OrderContentViewHolder) holder;
                contentHolder.setContentData(data.getContentData());
                if (data.isTitle()) {
                    contentHolder.setTitle();
                }
                break;
            case FOOTER:
                OrderFooterViewHolder footerHolder = (OrderFooterViewHolder) holder;
                footerHolder.setFooterData(data.getFooterData());
                footerHolder.setOnClickToActionButton(actionOnClickListener(data.getOrder()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public List<OrderData> getDataList() {
        return dataList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

    protected abstract View.OnClickListener actionOnClickListener(Order o);

}
