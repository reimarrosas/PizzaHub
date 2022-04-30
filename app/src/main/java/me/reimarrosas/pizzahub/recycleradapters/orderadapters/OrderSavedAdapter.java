package me.reimarrosas.pizzahub.recycleradapters.orderadapters;

import android.content.Context;
import android.view.View;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import me.reimarrosas.pizzahub.fragments.base.OrderSavedFragmentDirections;
import me.reimarrosas.pizzahub.models.Order;

public class OrderSavedAdapter extends OrderAdapter {

    public OrderSavedAdapter(Context context) {
        super(context);
    }

    @Override
    protected View.OnClickListener actionOnClickListener(Order o) {
        return view -> {
            NavDirections action = OrderSavedFragmentDirections
                    .actionOrderSavedFragmentToOrderComboFragment(o);
            Navigation.findNavController(view).navigate(action);
        };
    }
}
