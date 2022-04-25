package me.reimarrosas.pizzahub.contracts;

import androidx.annotation.NonNull;

import java.util.List;

import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Order;

public interface Notifiable {

    default void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
    }

    default <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
    }

    default void notifyOperationSuccess(Throwable t) {
    }

    default void notifyUpdatedOrders(List<Order> orders) {
    }

}
