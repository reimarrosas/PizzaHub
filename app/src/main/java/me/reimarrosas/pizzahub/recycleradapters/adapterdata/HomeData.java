package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import me.reimarrosas.pizzahub.models.MenuItem;

public class HomeData {

    private DataType type;
    private String headerTitle;
    private MenuItem menuItem;

    public HomeData(DataType type, String headerTitle, MenuItem item) {
        this.type = type;
        this.headerTitle = headerTitle;
        this.menuItem = item;
    }

    public DataType getType() {
        return type;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public enum DataType {
        HEADER, MENU_ITEM
    }

}
