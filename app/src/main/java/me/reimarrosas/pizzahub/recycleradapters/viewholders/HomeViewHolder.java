package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.reimarrosas.pizzahub.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private ImageView thumbNail;
    private TextView itemName;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        thumbNail = itemView.findViewById(R.id.imageViewItemImage);
        itemName = itemView.findViewById(R.id.textViewItemName);
    }

    public ImageView getThumbNail() {
        return thumbNail;
    }

    public void setItemName(String name) {
        itemName.setText(name);
    }

}
