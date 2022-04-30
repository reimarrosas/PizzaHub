package me.reimarrosas.pizzahub.recycleradapters.orderadapters;

import android.content.Context;
import android.view.View;

import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.services.OrderService;

public class OrderAdminAdapter extends OrderAdapter {

    private OrderService service;

    public OrderAdminAdapter(Context context, OrderService service) {
        super(context);

        this.service = service;
    }

    @Override
    protected View.OnClickListener actionOnClickListener(Order o, int position) {
        return view -> {
            service.changeOrderStatus(o);
            service.fetchData("orders");
        };
    }

}
