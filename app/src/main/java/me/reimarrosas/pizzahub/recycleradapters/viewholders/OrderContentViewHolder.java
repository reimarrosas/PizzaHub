package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.models.Triple;

public class OrderContentViewHolder extends RecyclerView.ViewHolder {

    private TextView sideName;
    private TextView toppingName;
    private TextView drinkName;

    public OrderContentViewHolder(@NonNull View itemView) {
        super(itemView);

        sideName = itemView.findViewById(R.id.textViewOrderSide);
        toppingName = itemView.findViewById(R.id.textViewOrderTopping);
        drinkName = itemView.findViewById(R.id.textViewOrderDrink);
    }

    public void setContentData(Triple<String, String, String> contentData) {
        sideName.setText(contentData.getData1());
        toppingName.setText(contentData.getData2());
        drinkName.setText(contentData.getData3());
    }

    public void setTitle() {
        sideName.setTypeface(sideName.getTypeface(), Typeface.BOLD);
        sideName.setTextSize(16);
        toppingName.setTypeface(toppingName.getTypeface(), Typeface.BOLD);
        toppingName.setTextSize(16);
        drinkName.setTypeface(drinkName.getTypeface(), Typeface.BOLD);
        drinkName.setTextSize(16);
    }

}
