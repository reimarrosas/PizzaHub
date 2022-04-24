package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import me.reimarrosas.pizzahub.models.MenuItem;

public class OrderSummaryData {

    private MenuItem data;
    private String title;
    private DataType type;

    public OrderSummaryData(MenuItem data, String title, DataType type) {
        this.data = data;
        this.title = title;
        this.type = type;
    }

    public MenuItem getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public DataType getType() {
        return type;
    }

    public enum DataType {
        SECTION_HEADER, MENU_ITEM
    }

}
