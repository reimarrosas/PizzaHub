package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Checkable;

public class DefaultViewHolder extends RecyclerView.ViewHolder implements Checkable {

    private ImageView thumbNail;
    private TextView itemName;
    private MaterialCardView card;

    public DefaultViewHolder(@NonNull View itemView) {
        super(itemView);

        card = itemView.findViewById(R.id.cardView);
        thumbNail = itemView.findViewById(R.id.imageViewItemImage);
        itemName = itemView.findViewById(R.id.textViewItemName);
    }

    @Override
    public void updateDataCheckedState() {
        card.setChecked(!card.isChecked());
    }

    public ImageView getThumbNail() {
        return thumbNail;
    }


    public void setName(String name) {
        itemName.setText(name);
    }

    public void addCheckListener(View.OnClickListener ocl) {
        card.setOnClickListener(ocl);
    }

}
