package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Pair;

public class OrderHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView orderId;
    private TextView orderStatus;

    public OrderHeaderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderId = itemView.findViewById(R.id.textViewOrderId);
        orderStatus = itemView.findViewById(R.id.textViewOrderStatus);
    }

    public void setHeaderData(Pair<String, String> headerData) {
        orderId.setText("ID: " + headerData.getData1());
        String status = headerData.getData2();
        orderStatus.setText(status);
        orderStatus.setTextColor(
                status.equalsIgnoreCase("pending")
                        ? Color.RED
                        : Color.GREEN);
    }

}
