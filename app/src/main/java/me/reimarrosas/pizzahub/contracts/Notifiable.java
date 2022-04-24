package me.reimarrosas.pizzahub.contracts;

import androidx.annotation.NonNull;

import java.util.List;

import me.reimarrosas.pizzahub.models.MenuItem;

public interface Notifiable {

    void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type);

    <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type);

    void notifyOperationSuccess(Throwable t);

}
