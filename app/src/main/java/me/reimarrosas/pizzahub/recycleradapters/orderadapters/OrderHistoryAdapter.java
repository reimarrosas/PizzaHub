package me.reimarrosas.pizzahub.recycleradapters.orderadapters;

import android.content.Context;
import android.view.View;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import me.reimarrosas.pizzahub.fragments.base.OrderHistoryFragmentDirections;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.recycleradapters.orderadapters.OrderAdapter;

public class OrderHistoryAdapter extends OrderAdapter {

    public OrderHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected View.OnClickListener actionOnClickListener(Order o, int position) {
        return view -> {
            NavDirections action = OrderHistoryFragmentDirections
                    .actionOrderHistoryFragmentToOrderComboFragment(o);
            Navigation.findNavController(view).navigate(action);
        };
    }

}