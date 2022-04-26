package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Single;

public class OrderFooterViewHolder extends RecyclerView.ViewHolder {

    private MaterialButton actionButton;

    public OrderFooterViewHolder(@NonNull View itemView) {
        super(itemView);

        actionButton = itemView.findViewById(R.id.buttonOrderAction);
    }

    public void setFooterData(Single<String> footerData) {
        actionButton.setText(footerData.getData());
    }

    public void setOnClickToActionButton(View.OnClickListener ocl) {
        actionButton.setOnClickListener(ocl);
    }

}
