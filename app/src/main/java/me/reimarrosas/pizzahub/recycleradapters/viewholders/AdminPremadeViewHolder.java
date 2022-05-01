package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.reimarrosas.pizzahub.R;

public class AdminPremadeViewHolder extends RecyclerView.ViewHolder {

    private ImageView premadeImage;
    private TextView premadeName;
    private TextView premadePrice;

    public AdminPremadeViewHolder(@NonNull View itemView) {
        super(itemView);

        premadeImage = itemView.findViewById(R.id.imageViewPremadeImage);
        premadeName = itemView.findViewById(R.id.textViewPremadeName);
        premadePrice = itemView.findViewById(R.id.textViewPremadeToppingPrice);
    }

    public ImageView getPremadeImage() {
        return premadeImage;
    }

    public void setNameAndPrice(String name, String price) {
        premadeName.setText(name);
        premadePrice.setText(price);
    }

    public void setOnViewClick(View.OnClickListener ocl) {
        itemView.setOnClickListener(ocl);
    }

}
