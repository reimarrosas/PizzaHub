package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Extras;
import me.reimarrosas.pizzahub.models.Topping;

public class ItemCrudViewHolder<T extends Extras> extends RecyclerView.ViewHolder {

    private ImageView itemImage;
    private TextView itemName;
    private TextView itemPrice;
    private TextView itemType;

    private final Context context;

    public ItemCrudViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        itemImage = itemView.findViewById(R.id.imageViewExtraImage);
        itemName = itemView.findViewById(R.id.textViewName);
        itemPrice = itemView.findViewById(R.id.textViewItemPrice);
        itemType = itemView.findViewById(R.id.textViewType);

        this.context = context;
    }

    public void setData(T model) {
        if (model instanceof Topping) {
            itemType.setText(((Topping) model).getType());
        } else {
            itemType.setText("");
        }

        itemName.setText(model.getName());
        itemPrice.setText(Double.toString(model.getPrice()));

        loadImage(model.getImageUrl());
    }

    public void setOnViewClick(View.OnClickListener ocl) {
        itemView.setOnClickListener(ocl);
    }

    public void unsetTypeTitle() {
        ((TextView) itemView.findViewById(R.id.textViewTypeTitle)).setText("");
    }

    private void loadImage(String imageUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(itemImage);
    }

}
