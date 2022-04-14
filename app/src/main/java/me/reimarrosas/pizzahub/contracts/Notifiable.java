package me.reimarrosas.pizzahub.contracts;

import java.util.List;

import me.reimarrosas.pizzahub.models.MenuItem;

public interface Notifiable {

    void notifyUpdatedData(List<? extends MenuItem> items, MenuItem.MenuItemType type);

    <T extends MenuItem> void notifyUpdatedData(T item, MenuItem.MenuItemType type);

}
